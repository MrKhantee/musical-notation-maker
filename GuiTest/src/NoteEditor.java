import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Robot;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.JTextComponent;
import javax.swing.text.LabelView;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.Position;
import javax.swing.text.StyleContext.NamedStyle;
import javax.imageio.*;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.ButtonGroup;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;




public class NoteEditor extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JToggleButton selectedNoteButton;   //中介者
	private JToggleButton selectedTuneButton;   //中介者
	private JScrollPane scrollPane;
	protected Vector Content;
	protected Shape PreTieShape;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoteEditor frame = new NoteEditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NoteEditor() {
		setTitle("簡譜編輯器");
		Content=new Vector();
		PreTieShape=null;
		final MutableAttributeSet attr = new SimpleAttributeSet(); 
        Style style=new StyleContext().new NamedStyle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 493);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setFont(new Font("新細明體", Font.PLAIN, 15));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Load");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					JFileChooser fc = new JFileChooser();
					//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int choose=fc.showSaveDialog(NoteEditor.this);
					if(choose == JFileChooser.APPROVE_OPTION)
					{
						File LoadPath = fc.getSelectedFile();
						BufferedReader reader=new BufferedReader(new FileReader(LoadPath));;
						String TempString;
						FormatTransformer Transformer=new FormatTransformer();
						while ((TempString = reader.readLine()) != null)
						{
							Transformer.FormatToNote(TempString,textPane.getStyledDocument());
						}
						reader.close(); 
					}
					
				}
				catch(Exception ex){}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save as img");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					JFileChooser fc = new JFileChooser();
					//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setSelectedFile(new File("C:\\*.jpg")); 
					int choose=fc.showSaveDialog(NoteEditor.this);
					if(choose == JFileChooser.APPROVE_OPTION)
					{
						File savePath = fc.getSelectedFile();
						Rectangle textRec=scrollPane.getBounds();
						Point textRecXY=new Point(textRec.x,textRec.y);
						Point textRecWH=new Point(textRec.width,textRec.height);
						SwingUtilities.convertPointToScreen(textRecXY,contentPane);
						textRec.setBounds(textRecXY.x,textRecXY.y,textRecWH.x,textRecWH.y);
						BufferedImage bi = new Robot().createScreenCapture(textRec);
			            ImageIO.write(bi, "jpg",savePath);
					}
				}
				catch(Exception ex){}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					JFileChooser fc = new JFileChooser();
					//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setSelectedFile(new File("C:\\*.song")); 
					int choose=fc.showSaveDialog(NoteEditor.this);
					if(choose == JFileChooser.APPROVE_OPTION)
					{
						File savePath = fc.getSelectedFile();
						FileWriter fileWriter=new FileWriter(savePath);
						String PreNote=null;
						for(int i=0;i<textPane.getText().length();i++)
						{
							if(Content.get(i)!=null)
							{
								if(PreNote!=null && PreNote.matches("[A-G|R].*")) fileWriter.write(" ");
								if(Content.get(i).toString().matches("[A-G|R|\\|].*")) fileWriter.write(Content.get(i).toString());
								PreNote=Content.get(i).toString();
							}
						}
						fileWriter.close(); 
					}
					
				}
				catch(Exception ex){}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 118, 638, 293);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
			}
		});
		scrollPane.setViewportView(textPane);
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(textPane.getCaretPosition()-1>=0)
				{
					int preText=-1;
					try
					{
						preText = textPane.getStyledDocument().getText(textPane.getCaretPosition()-1,1).charAt(0)-'0';
						if(preText>=0 && preText<=7)
						{
							textPane.setCaretPosition(textPane.getCaretPosition()+1);
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					} 

				}

			}
		});

		textPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane.setEditorKit(new StyledEditorKit() { 
            public ViewFactory getViewFactory() { 
                return new NewViewFactory(NoteEditor.this); 

            } 
        }); 
        textPane.setStyledDocument(new CustomDocument());
        
        textPane.setCaret(new DefaultCaret() {

            public void paint(Graphics g) {

                JTextComponent comp = getComponent();
                if (comp == null)
                    return;

                Rectangle r = null;
                try {
                    r = comp.modelToView(getDot());
                    if (r == null)
                        return;
                } catch (BadLocationException e) {
                    return;
                }
                r.height = 19; //this value changes the caret size
                if (isVisible())
                    g.fillRect(r.x, r.y, 1, r.height);
            }
        });	
        textPane.getCaret().setBlinkRate(500);
        

	
        textPane.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if(!textPane.getInputAttributes().isEqual(attr)){
        		//textPane.getInputAttributes().addAttributes(attr);
        		textPane.setCharacterAttributes(attr,false);
        		textPane.repaint();
        		}
        		/*if(e.getKeyChar()=='\b')
        		{
        			textPane.setCaretPosition(textPane.getCaretPosition()-1);
        		}*/
        	}
        	/*@Override
        	public void keyReleased(KeyEvent e) {
        		if((e.getKeyCode()>=48 && e.getKeyCode()<=57) || (e.getKeyCode()>=96 && e.getKeyCode()<=105))
        		{
        			try
        			{
        				if(textPane.getText()!=null && textPane.getText().matches(".*\\d\\/\\d.*"))
        				textPane.getStyledDocument().insertString(textPane.getCaretPosition(), " ",null );
        			}
        			catch(Exception error) {}
        		}
        				
        	}*/
        });
        StyleConstants.setSpaceAbove(style, 20);
        StyleConstants.setSpaceBelow(style, 10);
        StyleConstants.setLeftIndent(style, 10);
        StyleConstants.setRightIndent(style,10);
        StyleConstants.setFontFamily(style, "Courier New");
        StyleConstants.setFontSize(style, 20);
        textPane.setLogicalStyle(style);
		
		JButton btnSharp = new JButton("Sharp");
		btnSharp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StyledDocument doc = textPane.getStyledDocument();
				try
				{
				    doc.insertString(doc.getLength(), "#",null );
				}
				catch(Exception e) { System.out.println(e); }
			}
		});
		btnSharp.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnSharp.setBounds(40, 52, 119, 23);
		contentPane.add(btnSharp);
		
		String[] Notes = {"4th","8th","16th","32th"};
		JComboBox comboBox = new JComboBox(Notes);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1th", "2th", "4th", "8th", "16th", "32th"}));
		comboBox.setSelectedIndex(2);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) 
				{
					String Note=arg0.getItem().toString();
					if(Note=="1th")
					{
						attr.addAttribute("Note",new Integer(-2)); 
						textPane.setCharacterAttributes(attr,false);
					}
					if(Note=="2th")
					{
						attr.addAttribute("Note",new Integer(-1)); 
						
						textPane.setCharacterAttributes(attr,false);
					}
					if(Note=="4th")
					{
						attr.addAttribute("Note",new Integer(0)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="8th")
					{
						attr.addAttribute("Note",new Integer(1)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="16th")
					{
						attr.addAttribute("Note",new Integer(2)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="32th")
					{
						attr.addAttribute("Note",new Integer(3)); 
						textPane.setCharacterAttributes(attr,false);
					}
				}
				
			}
		});
		comboBox.setBounds(90, 21, 111, 21);
		contentPane.add(comboBox);
		
		JButton btnFalt = new JButton("Flat");
		btnFalt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StyledDocument doc = textPane.getStyledDocument();
				try
				{
				    doc.insertString(doc.getLength(), "b",null );
				}
				catch(Exception e) { System.out.println(e); }
				
			}
		});
		btnFalt.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFalt.setBounds(169, 52, 119, 23);
		contentPane.add(btnFalt);
		
		JComboBox comboBox_1 = new JComboBox(new Object[]{});
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) 
				{
					String Note=arg0.getItem().toString();
					if(Note=="None")
					{
						attr.addAttribute("Tune",new Integer(0)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="H")
					{
						attr.addAttribute("Tune",new Integer(1)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="HH")
					{
						attr.addAttribute("Tune",new Integer(2)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="HHH")
					{
						attr.addAttribute("Tune",new Integer(3)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="L")
					{
						attr.addAttribute("Tune",new Integer(-1)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="LL")
					{
						attr.addAttribute("Tune",new Integer(-2)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="LLL")
					{
						attr.addAttribute("Tune",new Integer(-3)); 
						textPane.setCharacterAttributes(attr,false);
					}
				}
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"None", "H", "HH", "HHH", "L", "LL", "LLL"}));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(226, 21, 111, 21);
		contentPane.add(comboBox_1);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MusicHandler Handler=new MusicHandler(Content);
				Handler.Run();
			}
		});
		btnRun.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRun.setBounds(310, 52, 119, 23);
		contentPane.add(btnRun);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Dot");
		tglbtnNewToggleButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				int state = arg0.getStateChange();
			    if (state == ItemEvent.SELECTED) 
			    {
			    	attr.addAttribute("Dot",new Integer(1)); 
			    	textPane.setCharacterAttributes(attr,false);
			    }
			    else
			    {
			    	attr.addAttribute("Dot",new Integer(0)); 
			    	textPane.setCharacterAttributes(attr,false);
			    }
			}
		});

		tglbtnNewToggleButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton.setBounds(453, 52, 119, 23);
		contentPane.add(tglbtnNewToggleButton);
		
		JLabel lblNewLabel = new JLabel("音符:");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		lblNewLabel.setBounds(35, 21, 39, 15);
		contentPane.add(lblNewLabel);
		
		/*JButton btnTie = new JButton("Tie");
		btnTie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textPane.getSelectedText()!=null)
				{
					String s=textPane.getSelectedText();
					if(textPane.getSelectedText().matches("[^1-7]*[1-7][^1-7]*[1-7][^1-7]*"))
					{
				    	attr.addAttribute("Tie",new Integer(1)); 
				    	textPane.getStyledDocument().setCharacterAttributes(textPane.getSelectionStart(), s.length(), attr,false);
				    	
				    	//textPane.setCharacterAttributes(attr,false);
					}
				}
			}
		});*/
		/*btnTie.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnTie.setBounds(452, 76, 119, 23);
		contentPane.add(btnTie);*/
		textPane.getStyledDocument().addDocumentListener(new DocumentListener(){
		      public void changedUpdate(DocumentEvent documentEvent) {
		        }
		      public void insertUpdate(DocumentEvent documentEvent) {
	        }
	        public void removeUpdate(DocumentEvent documentEvent) {
	        	int a=0;
	        }
			
		});
				
	}
}

class NewViewFactory implements ViewFactory { 
	private NoteEditor Editor;
	NewViewFactory(NoteEditor e)
	{
		Editor=e;
	}
	
    public View create(Element elem) { 
        String kind = elem.getName(); 
        if (kind != null) { 
            if (kind.equals(AbstractDocument.ContentElementName)) { 

                return new MyLabelView(elem,Editor); 
            } 
            else if (kind.equals(AbstractDocument.ParagraphElementName)) { 
                return new ParagraphView(elem); 
            } 
            else if (kind.equals(AbstractDocument.SectionElementName)) { 
                return new BoxView(elem, View.Y_AXIS); 

            } 
            else if (kind.equals(StyleConstants.ComponentElementName)) { 
                return new ComponentView(elem); 
            } 
            else if (kind.equals(StyleConstants.IconElementName)) { 
                return new IconView(elem); 
            } 

        } 
        return new LabelView(elem); 
    } 
} 
 
class MyLabelView extends LabelView { 
	private NoteEditor Editor;
 
    public MyLabelView(Element elem,NoteEditor e) { 
        super(elem); 
        Editor=e;
    } 
 
    public void paint(Graphics g, Shape allocation) { 
        super.paint(g, allocation); 
        paintLine(g, allocation); 

    } 
 
    public void paintLine(Graphics g, Shape a) { 
    	
    	Integer Note=((Integer)getElement().getAttributes().getAttribute("Note")); 
    	Integer Tune=((Integer)getElement().getAttributes().getAttribute("Tune")); 
    	Integer Dot=((Integer)getElement().getAttributes().getAttribute("Dot")); 
    	//Integer Tie=((Integer)getElement().getAttributes().getAttribute("Tie")); 
        int End=getEndOffset();
        Shape charShape=null;
        int x1=0,x2=0,lineCount=0,tuneCount=0,dotCount=0,tieCount=0;

        /*if(Tie!=null && Tie.intValue()>0)
        {
            for(int Start=getStartOffset();Start+1<=End;Start++)
            {
            	try
            	{
            		String charToDraw=getText(Start,Start+1).toString();
            		if(!charToDraw.matches("[1-7]+")) continue;
            		charShape=modelToView(Start,a,Position.Bias.Forward);
            		if(Editor.PreTieShape==null)
            		{
            			Editor.PreTieShape=charShape;
            		}
            		else
            		{
            			g.drawArc(Editor.PreTieShape.getBounds().x+7, Editor.PreTieShape.getBounds().y-15, charShape.getBounds().x-Editor.PreTieShape.getBounds().x-2,charShape.getBounds().height-15,0,180);
            			//g.drawLine(Editor.PreTieShape.getBounds().x, Editor.PreTieShape.getBounds().y, charShape.getBounds().x, charShape.getBounds().y);
            			Editor.PreTieShape=null;
            		}
             	}
            	catch(Exception e) { e.printStackTrace(); }
            	
            }
        }*/
        
        if(Dot!=null && Dot.intValue()>0)
        {
        	int y = a.getBounds().y + a.getBounds().height - (int) getGlyphPainter().getDescent(this); 
            for(int Start=getStartOffset();Start+1<=End;Start++)
            {
            	try
            	{
            		String charToDraw=getText(Start,Start+1).toString();
            		if(!charToDraw.matches("[1-7]+")) continue;
            		charShape=modelToView(Start,a,Position.Bias.Forward);
            		x1=(int) charShape.getBounds().getX(); 
            		charShape=modelToView(Start+1,a,Position.Bias.Forward);
            		x2=(int) charShape.getBounds().getX(); 
            		g.fillOval(x2,y,3,3);

             	}
            	catch(Exception e) { e.printStackTrace(); }
            	
            }
        }
        
        if (Note!=null) { 
            int y = a.getBounds().y + a.getBounds().height - (int) getGlyphPainter().getDescent(this); 
            lineCount=Note.intValue();
            //y = y - (int) (getGlyphPainter().getAscent(this) * 0.3f); 
            for(int Start=getStartOffset();Start+1<=End;Start++)
            {
            	try
            	{
            		String charToDraw=getText(Start,Start+1).toString();
            		if(!charToDraw.matches("[1-7]+")) continue;
            		charShape=modelToView(Start,a,Position.Bias.Forward);
            		x1=(int) charShape.getBounds().getX(); 
            		charShape=modelToView(Start+1,a,Position.Bias.Forward);
            		x2=(int) charShape.getBounds().getX(); 
                    if(lineCount<0)
                    {
                		if(lineCount==-1)g.drawLine(x2, y-5,x2+5, y-5); 
                		else
                		{
                			g.drawLine(x2, y-5,x2+1, y-5); 
                			g.drawLine(x2+3, y-5,x2+4, y-5); 
                			g.drawLine(x2+6, y-5,x2+7, y-5); 
                		}
                    }
                    else if(lineCount>0)
                    {
                		for(int i=0;i<lineCount;i++)
                		{
                    		g.drawLine(x1+1, y+2*i+1, x2-1, y+2*i+1); 
                		}
                    }

             	}
            	catch(Exception e) { e.printStackTrace(); }
            	
            }
        }
        
        
        if (Tune!=null) { 
            tuneCount=Tune.intValue();
            if(tuneCount>0)
            {
            	int y = a.getBounds().y + a.getBounds().height; 
            	y = y - (int) (getGlyphPainter().getAscent(this)); 
            	for(int Start=getStartOffset();Start+1<=End;Start++)
            	{
            		try
            		{
            			String charToDraw=getText(Start,Start+1).toString();
            			if(!charToDraw.matches("[1-7]+")) continue;
            			charShape=modelToView(Start,a,Position.Bias.Forward);
            			x1=(int) charShape.getBounds().getX(); 
            			charShape=modelToView(Start+1,a,Position.Bias.Forward);
            			x2=(int) charShape.getBounds().getX(); 
            			for(int i=0;i<tuneCount;i++)
            			{
            				g.fillOval(x1+3, y-3*i-7, 3,3);
            			}

            		}
            	catch(Exception e) { e.printStackTrace(); }
            	
            	}
            }
            else
            {
            	tuneCount=Math.abs(tuneCount);
            	int y = a.getBounds().y + a.getBounds().height - (int) getGlyphPainter().getDescent(this); 
            	for(int Start=getStartOffset();Start+1<=End;Start++)
            	{
            		try
            		{
            			String charToDraw=getText(Start,Start+1).toString();
            			if(!charToDraw.matches("[1-7]+")) continue;
            			charShape=modelToView(Start,a,Position.Bias.Forward);
            			x1=(int) charShape.getBounds().getX(); 
            			charShape=modelToView(Start+1,a,Position.Bias.Forward);
            			x2=(int) charShape.getBounds().getX(); 
            			for(int i=0;i<tuneCount;i++)
            			{
            				g.fillOval(x1+3, y+3*i+1+(2*lineCount+1), 3,3);
            			}

            		}
            	catch(Exception e) { e.printStackTrace(); }
            	
            	}
            	tuneCount=Tune.intValue();
            }
        }
        String StringToTransform=getText(getStartOffset(),getEndOffset()).toString();
        FormatTransformer Transformer=new FormatTransformer();
    	Vector temp;
    	if(lineCount<0)
    	{
        	if(lineCount==-1)	temp=Transformer.NoteTransform(StringToTransform,2,tuneCount,dotCount);
        	else temp=Transformer.NoteTransform(StringToTransform,1,tuneCount,dotCount);
    	}
    	else   	temp=Transformer.NoteTransform(StringToTransform,(int)(4*Math.pow(2,lineCount)),tuneCount,dotCount);
    	int index=0;
    	for(int start=0;start<StringToTransform.length();start++)
    	{
    			if(getStartOffset()+start>=Editor.Content.size())
    			{
    				Editor.Content.setSize(Editor.Content.size()+10);
    			}
    			Editor.Content.setElementAt(temp.get(index),getStartOffset()+start);
    			index++;
    	}

        
    } 
} 


class CustomDocument extends DefaultStyledDocument {
    @Override
    public void insertString(int offset, String string, AttributeSet attributeSet)
            throws BadLocationException {
    	if(offset-1>=0)
    	{
    		if(Character.isDigit(this.getText(offset-1,1).charAt(0)) && Character.isDigit(string.charAt(0)))
    		{
    			super.insertString(offset," "+string+" ", attributeSet);
    		}
    		else if(Character.isDigit(string.charAt(0)))
    		{
    			super.insertString(offset, string+" ", attributeSet);
    		}
            else
            {
            	super.insertString(offset, string, attributeSet);
            }
    	}
    	else if(Character.isDigit(string.charAt(0)))
        {
            super.insertString(offset, string+" ", attributeSet);
        }
        else
        {
        	super.insertString(offset, string, attributeSet);
        }
    }
}