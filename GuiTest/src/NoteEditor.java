import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Robot;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JToolBar;
import javax.swing.JTextField;




public class NoteEditor extends JFrame {


	private JPanel contentPane;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	protected Vector Content;
	protected Shape PreTieShape;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;

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
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){}
		JFrame.setDefaultLookAndFeelDecorated(true);
		Content=new Vector();
		PreTieShape=null;
		final MutableAttributeSet attr = new SimpleAttributeSet(); 
        Style style=new StyleContext().new NamedStyle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 541);
		
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
					fc.setSelectedFile(new File("C:\\*.song")); 
					int choose=fc.showOpenDialog(NoteEditor.this);
					int timeFlag=0;
					if(choose == JFileChooser.APPROVE_OPTION)
					{
						textPane.setText("");
						File LoadPath = fc.getSelectedFile();
						BufferedReader reader=new BufferedReader(new FileReader(LoadPath));;
						String TempString;
						FormatTransformer Transformer=new FormatTransformer();
						while ((TempString = reader.readLine()) != null)
						{
							if(timeFlag==0)
							{
								textField.setText(TempString);
								timeFlag=1;
							}
							else Transformer.FormatToNote(TempString,textPane.getStyledDocument());
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
					fc.setSelectedFile(new File("C:\\*.song")); 
					int choose=fc.showSaveDialog(NoteEditor.this);
					if(choose == JFileChooser.APPROVE_OPTION)
					{
						File savePath = fc.getSelectedFile();
						FileWriter fileWriter=new FileWriter(savePath);
						String PreNote=null;
						
						fileWriter.write(textField.getText()+System.getProperty("line.separator"));
						for(int i=0;i<textPane.getText().length();i++)
						{
							if(Content.get(i)!=null)
							{
								if(PreNote!=null && PreNote.matches("[A-GR].*") && Content.get(i).toString().matches("[A-GR].*"))  fileWriter.write(" ");
								if(Content.get(i).toString().matches("[A-G|R].*"))
								{
									fileWriter.write(Content.get(i).toString());
									PreNote=Content.get(i).toString();
								}
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
		scrollPane.setBounds(10, 118, 684, 354);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			    if(textPane.getSelectedText()!=null)
			    {
			    	textPane.setCharacterAttributes(attr, false);
			    	textPane.repaint();
			    }
			}
		});
		textPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {

			}
		});
		textPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {

			}
		});

		scrollPane.setViewportView(textPane);
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
                r.height = 19; 
                if (isVisible())
                    g.fillRect(r.x, r.y, 1, r.height);
            }
        });	
        textPane.getCaret().setBlinkRate(500);
        

	
        textPane.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if(!textPane.getInputAttributes().isEqual(attr)){
        		textPane.setCharacterAttributes(attr,false);
        		textPane.repaint();
        		}
        	}
        });
        StyleConstants.setSpaceAbove(style, 20);
        StyleConstants.setSpaceBelow(style, 10);
        StyleConstants.setLeftIndent(style, 10);
        StyleConstants.setRightIndent(style,10);
        StyleConstants.setFontFamily(style, "Courier New");
        StyleConstants.setFontSize(style, 20);
        textPane.setLogicalStyle(style);
		
		String[] Notes = {"4th","8th","16th","32th"};
		
		JComboBox comboBox_1 = new JComboBox(new Object[]{});
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) 
				{
					String Note=arg0.getItem().toString();
					if(Note=="+0")
					{
						attr.addAttribute("Tune",new Integer(0)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="+8")
					{
						attr.addAttribute("Tune",new Integer(1)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="+16")
					{
						attr.addAttribute("Tune",new Integer(2)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="+24")
					{
						attr.addAttribute("Tune",new Integer(3)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="-8")
					{
						attr.addAttribute("Tune",new Integer(-1)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="-16")
					{
						attr.addAttribute("Tune",new Integer(-2)); 
						textPane.setCharacterAttributes(attr,false);
					}
					else if(Note=="-24")
					{
						attr.addAttribute("Tune",new Integer(-3)); 
						textPane.setCharacterAttributes(attr,false);
					}
				}
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"+0", "+8", "+16", "+24", "-8", "-16", "-24"}));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(598, 53, 96, 34);
		contentPane.add(comboBox_1);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MusicHandler Handler=new MusicHandler(Content,textPane.getText().length());
				Handler.Run();
			}
		});
		btnRun.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRun.setBounds(402, 59, 119, 23);
		contentPane.add(btnRun);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 0, 704, 42);
		contentPane.add(toolBar);
		
		JToggleButton toggleButton_5 = new JToggleButton("");
		toggleButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attr.addAttribute("Note",new Integer(-2)); 
				textPane.setCharacterAttributes(attr,false);
			}
		});
		buttonGroup.add(toggleButton_5);
		toolBar.add(toggleButton_5);
		toggleButton_5.setIcon(new ImageIcon(".\\img\\1.gif"));
		
		JToggleButton toggleButton = new JToggleButton("");
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attr.addAttribute("Note",new Integer(-1)); 
				textPane.setCharacterAttributes(attr,false);
			}
		});
		buttonGroup.add(toggleButton);
		toolBar.add(toggleButton);
		toggleButton.setIcon(new ImageIcon(".\\img\\2.gif"));
		
		JToggleButton toggleButton_1 = new JToggleButton("");
		toggleButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attr.addAttribute("Note",new Integer(0)); 
				textPane.setCharacterAttributes(attr,false);
			}
		});
		toggleButton_1.setSelected(true);
		buttonGroup.add(toggleButton_1);
		toolBar.add(toggleButton_1);
		toggleButton_1.setIcon(new ImageIcon(".\\img\\3.gif"));
		
		JToggleButton toggleButton_2 = new JToggleButton("");
		toggleButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attr.addAttribute("Note",new Integer(1)); 
				textPane.setCharacterAttributes(attr,false);
			}
		});
		buttonGroup.add(toggleButton_2);
		toolBar.add(toggleButton_2);
		toggleButton_2.setIcon(new ImageIcon(".\\img\\4.gif"));
		
		JToggleButton toggleButton_3 = new JToggleButton("");
		toggleButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attr.addAttribute("Note",new Integer(2)); 
				textPane.setCharacterAttributes(attr,false);
			}
		});
		buttonGroup.add(toggleButton_3);
		toolBar.add(toggleButton_3);
		toggleButton_3.setIcon(new ImageIcon(".\\img\\5.gif"));
		
		JToggleButton toggleButton_4 = new JToggleButton("");
		toggleButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attr.addAttribute("Note",new Integer(3)); 
				textPane.setCharacterAttributes(attr,false);
			}
		});
		toolBar.add(toggleButton_4);
		buttonGroup.add(toggleButton_4);
		toggleButton_4.setIcon(new ImageIcon(".\\img\\6.gif"));
		
		JButton btnDot = new JButton("");
		toolBar.add(btnDot);
		btnDot.setIcon(new ImageIcon(".\\img\\dot.gif"));
		btnDot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    if(textPane.getSelectedText()!=null)
			    {
			    	MutableAttributeSet TempAttr = new SimpleAttributeSet(); 
			    	TempAttr.addAttribute("Dot", new Integer(1));
			    	textPane.getStyledDocument().setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd()-textPane.getSelectionStart(),TempAttr,false);
			    }
			}
		});
		
        
        
        
		JButton btnSharp = new JButton("#");
		toolBar.add(btnSharp);
		btnSharp.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		
		JButton btnFalt = new JButton("b");
		btnFalt.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		toolBar.add(btnFalt);
		
		JLabel lblNewLabel = new JLabel("音高:");
		lblNewLabel.setFont(new Font("標楷體", Font.BOLD, 18));
		lblNewLabel.setBounds(531, 42, 57, 52);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(116, 57, 103, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("拍號(N/N):");
		lblNewLabel_1.setFont(new Font("標楷體", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 56, 103, 31);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Check");
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String MusicString="",PreNote=null;
				for(int i=0;i<textPane.getText().length();i++)
				{
					if(Content.get(i)!=null)
					{
						if(PreNote!=null && PreNote.matches("[A-GR].*"))
						{
							MusicString=MusicString+" ";
						}
						if(Content.get(i).toString().matches("[A-G|R].*")) MusicString=MusicString+Content.get(i).toString();
						PreNote=Content.get(i).toString();
					}
				}
				checkBeat CheckBeater=new checkBeat(textField.getText(),MusicString);
				try
				{
					boolean CheckAns=CheckBeater.Start();
					if(CheckAns==true)
					{
						JOptionPane.showMessageDialog(NoteEditor.this,"節拍數正確");
					}
					else JOptionPane.showMessageDialog(NoteEditor.this,"節拍數有誤");
				}
				catch(Exception e){}
			}
		});
		btnNewButton.setBounds(246, 59, 119, 23);
		contentPane.add(btnNewButton);
		
		
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
        int End=getEndOffset();
        Shape charShape=null;
        int x1=0,x2=0,lineCount=0,tuneCount=0,dotCount=0;

        
        if(Dot!=null && Dot.intValue()>0)
        {
        	int y = a.getBounds().y + a.getBounds().height - (int) getGlyphPainter().getDescent(this); 
            for(int Start=getStartOffset();Start+1<=End;Start++)
            {
            	try
            	{
            		String charToDraw=getText(Start,Start+1).toString();
            		if(!charToDraw.matches("[0-7]+")) continue;
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
            for(int Start=getStartOffset();Start+1<=End;Start++)
            {
            	try
            	{
            		String charToDraw=getText(Start,Start+1).toString();
            		if(!charToDraw.matches("[0-7]+")) continue;
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
            			if(!charToDraw.matches("[0-7]+")) continue;
            			charShape=modelToView(Start,a,Position.Bias.Forward);
            			x1=(int) charShape.getBounds().getX(); 
            			charShape=modelToView(Start+1,a,Position.Bias.Forward);
            			x2=(int) charShape.getBounds().getX(); 
            			for(int i=0;i<tuneCount;i++)
            			{
            				g.fillOval(x1+3, y-3*i-8, 3,3);
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
            			if(!charToDraw.matches("[0-7]+")) continue;
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
    	if(this.getLength()>0 && offset-1>=0)
    	{
    		if(Character.isDigit(this.getText(offset-1,1).charAt(0)))
    		{
    			if(offset<this.getLength())
    			{
    				if(this.getText(offset,1)!=" ")
    				{
    	    			super.insertString(offset," ", attributeSet);
    	    			offset++;
    				}
    			}
    			
    			else
    			{
	    			super.insertString(offset," ", attributeSet);
	    			offset++;
    			}
    		}
    	}
    	if(Character.isDigit(string.charAt(0)))
    	{
    		super.insertString(offset, string+" ", attributeSet);
    	}
    	else
    	{
    		super.insertString(offset, string, attributeSet);
    	}

    }
}