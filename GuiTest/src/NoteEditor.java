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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
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
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.ButtonGroup;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;




public class NoteEditor extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JToggleButton tglbtnNewToggleButton_2;
	private JToggleButton tglbtnHtune;
	private JToggleButton tglbtnNewToggleButton_3;
	private JToggleButton tglbtnNewToggleButton_4;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JToggleButton tglbtnNewToggleButton_5;
	private JToggleButton tglbtnNewToggleButton_6;
	private JToggleButton tglbtnNewToggleButton_7;
	private JToggleButton selectedNoteButton;   //中介者
	private JToggleButton selectedTuneButton;   //中介者
	protected Vector Content;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textPane = new JTextPane();
		textPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane.setBounds(10, 166, 638, 279);
		contentPane.add(textPane);
		textPane.setEditorKit(new StyledEditorKit() { 
            public ViewFactory getViewFactory() { 
                return new NewViewFactory(NoteEditor.this); 

            } 
        }); 
		
        StyledDocument doc = (StyledDocument) textPane.getDocument(); 
        Style style=new StyleContext().new NamedStyle();
        StyleConstants.setSpaceAbove(style, 20);
        StyleConstants.setSpaceBelow(style, 10);
        StyleConstants.setLeftIndent(style, 10);
        textPane.setLogicalStyle(style);
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
                r.height = 17; //this value changes the caret size
                if (isVisible())
                    g.fillRect(r.x, r.y, 1, r.height);
            }
        });	
        textPane.getCaret().setBlinkRate(500);

       
		final MutableAttributeSet attr = new SimpleAttributeSet(); 
		attr.addAttribute("Note",new Integer(0)); 
		attr.addAttribute("Tune",new Integer(0)); 
		textPane.setCharacterAttributes(attr,false);
		
		JToggleButton tglbtnth = new JToggleButton("4th");
		selectedNoteButton=tglbtnth;
		tglbtnth.setSelected(true);
		tglbtnth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Note",new Integer(0)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedNoteButton!=null && selectedNoteButton!=toggle)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		buttonGroup.add(tglbtnth);
		tglbtnth.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglbtnth.setBounds(35, 10, 119, 23);
		contentPane.add(tglbtnth);
		
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("8th");
		buttonGroup.add(tglbtnNewToggleButton);
		tglbtnNewToggleButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Note",new Integer(1)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedNoteButton!=null && selectedNoteButton!=toggle)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton.setBounds(179, 10, 119, 23);
		contentPane.add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("16th");
		buttonGroup.add(tglbtnNewToggleButton_1);
		tglbtnNewToggleButton_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Note",new Integer(2)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedNoteButton!=null && selectedNoteButton!=toggle)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_1.setBounds(332, 10, 119, 23);
		contentPane.add(tglbtnNewToggleButton_1);
		
		tglbtnNewToggleButton_2 = new JToggleButton("32th");
		buttonGroup.add(tglbtnNewToggleButton_2);
		tglbtnNewToggleButton_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Note",new Integer(3)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedNoteButton!=null && selectedNoteButton!=toggle)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_2.setBounds(488, 10, 119, 23);
		contentPane.add(tglbtnNewToggleButton_2);
		
		tglbtnHtune = new JToggleButton("HTune");
		buttonGroup_1.add(tglbtnHtune);
		tglbtnHtune.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnHtune.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(1)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnHtune.setBounds(179, 43, 119, 23);
		contentPane.add(tglbtnHtune);
		
		tglbtnNewToggleButton_3 = new JToggleButton("HHTune");
		buttonGroup_1.add(tglbtnNewToggleButton_3);
		tglbtnNewToggleButton_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(2)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_3.setActionCommand("HHTune");
		tglbtnNewToggleButton_3.setBounds(332, 43, 119, 23);
		contentPane.add(tglbtnNewToggleButton_3);
		
		tglbtnNewToggleButton_4 = new JToggleButton("HHHTune");
		buttonGroup_1.add(tglbtnNewToggleButton_4);
		tglbtnNewToggleButton_4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(3)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_4.setBounds(488, 43, 119, 23);
		contentPane.add(tglbtnNewToggleButton_4);
		
		tglbtnNewToggleButton_5 = new JToggleButton("LTune");
		buttonGroup_1.add(tglbtnNewToggleButton_5);
		tglbtnNewToggleButton_5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(-1)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_5.setBounds(179, 76, 119, 23);
		contentPane.add(tglbtnNewToggleButton_5);
		
		tglbtnNewToggleButton_6 = new JToggleButton("LLTune");
		buttonGroup_1.add(tglbtnNewToggleButton_6);
		tglbtnNewToggleButton_6.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(-2)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_6.setBounds(332, 76, 119, 23);
		contentPane.add(tglbtnNewToggleButton_6);
		
		tglbtnNewToggleButton_7 = new JToggleButton("LLLTune");
		buttonGroup_1.add(tglbtnNewToggleButton_7);
		tglbtnNewToggleButton_7.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnNewToggleButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(-3)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_7.setBounds(488, 76, 119, 23);
		contentPane.add(tglbtnNewToggleButton_7);
		
		JButton tglbtnSave = new JButton("Save As img");
		tglbtnSave.addActionListener(new ActionListener() {
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
						Rectangle textRec=textPane.getBounds();
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
		tglbtnSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnSave.setBounds(35, 76, 119, 23);
		contentPane.add(tglbtnSave);
		
		JToggleButton tglbtnTune = new JToggleButton("Tune");
		selectedTuneButton=tglbtnTune;
		tglbtnTune.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(0)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null && selectedTuneButton!=toggle)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		buttonGroup_1.add(tglbtnTune);
		tglbtnTune.setSelected(true);
		tglbtnTune.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglbtnTune.setBounds(35, 43, 119, 23);
		contentPane.add(tglbtnTune);
		
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
		btnSharp.setBounds(35, 109, 119, 23);
		contentPane.add(btnSharp);
		
		String[] Notes = {"4th","8th","16th","32th"};
		JComboBox comboBox = new JComboBox(Notes);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) 
				{
					String Note=arg0.getItem().toString();
					if(Note=="8th")
					{
						attr.addAttribute("Note",new Integer(1)); 
						textPane.setCharacterAttributes(attr,false);
					}
				}
				
			}
		});
		comboBox.setBounds(496, 110, 111, 21);
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
		btnFalt.setBounds(179, 109, 119, 23);
		contentPane.add(btnFalt);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
						for(int i=0;i<Content.size();i++)
						{
							if(Content.get(i)!=null)
							{
								fileWriter.write(Content.get(i).toString()+" ");
							}
						}
						fileWriter.close(); 
					}
					
				}
				catch(Exception ex){}
			}
		});
		btnSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnSave.setBounds(332, 109, 119, 23);
		contentPane.add(btnSave);
		
		Content=new Vector();
		
		

		
		textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textPane.getInputAttributes()!=attr){
				textPane.setCharacterAttributes(attr,false);
				textPane.repaint();
				}
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
        int End=getEndOffset();
        Shape charShape=null;
        int x1=0,x2=0;
        if (Note!=null) { 
            int y = a.getBounds().y + a.getBounds().height - (int) getGlyphPainter().getDescent(this); 
            int lineCount=Note.intValue();
            //y = y - (int) (getGlyphPainter().getAscent(this) * 0.3f); 
            for(int Start=getStartOffset();Start+1<=End;Start++)
            {
            	try
            	{
            		String charToDraw=getText(Start,Start+1).toString();
            		if(!charToDraw.matches("[0-9]+")) continue;
            		charShape=modelToView(Start,a,Position.Bias.Forward);
            		x1=(int) charShape.getBounds().getX(); 
            		charShape=modelToView(Start+1,a,Position.Bias.Forward);
            		x2=(int) charShape.getBounds().getX(); 
            		for(int i=0;i<lineCount;i++)
            		{
                		g.drawLine(x1+1, y+2*i+1, x2-1, y+2*i+1); 
            		}

             	}
            	catch(Exception e) { e.printStackTrace(); }
            	
            }
        }
        
        
        if (Tune!=null) { 
            int DotCount=Tune.intValue();
            if(DotCount>0)
            {
            	int y = a.getBounds().y + a.getBounds().height; 
            	y = y - (int) (getGlyphPainter().getAscent(this)); 
            	for(int Start=getStartOffset();Start+1<=End;Start++)
            	{
            		try
            		{
            			String charToDraw=getText(Start,Start+1).toString();
            			if(!charToDraw.matches("[0-9]+")) continue;
            			charShape=modelToView(Start,a,Position.Bias.Forward);
            			x1=(int) charShape.getBounds().getX(); 
            			charShape=modelToView(Start+1,a,Position.Bias.Forward);
            			x2=(int) charShape.getBounds().getX(); 
            			for(int i=0;i<DotCount;i++)
            			{
            				g.fillOval(x1+1, y-3*i-5, 3,3);
            			}

            		}
            	catch(Exception e) { e.printStackTrace(); }
            	
            	}
            }
            else
            {
            	DotCount=Math.abs(DotCount);
            	int lineCount;
            	lineCount=Note.intValue();
            	int y = a.getBounds().y + a.getBounds().height - (int) getGlyphPainter().getDescent(this); 
            	for(int Start=getStartOffset();Start+1<=End;Start++)
            	{
            		try
            		{
            			String charToDraw=getText(Start,Start+1).toString();
            			if(!charToDraw.matches("[0-9]+")) continue;
            			charShape=modelToView(Start,a,Position.Bias.Forward);
            			x1=(int) charShape.getBounds().getX(); 
            			charShape=modelToView(Start+1,a,Position.Bias.Forward);
            			x2=(int) charShape.getBounds().getX(); 
            			for(int i=0;i<DotCount;i++)
            			{
            				g.fillOval(x1+1, y+3*i+1+(2*lineCount+1), 3,3);
            			}

            		}
            	catch(Exception e) { e.printStackTrace(); }
            	
            	}
            }
        }
        String StringToTransform=getText(getStartOffset(),getEndOffset()).toString();
        FormatTransformer Transformer=new FormatTransformer();
        if (Note!=null && Tune!=null) 
        {
        	Vector temp=Transformer.FormatTransform(StringToTransform,(int)(4*Math.pow(2,Note.intValue())),Tune.intValue());
        	int index=0;
        	for(int start=0;start<StringToTransform.length();start++)
        	{
        		if(StringToTransform.charAt(start)=='|')
        		{
        			if(getStartOffset()+start>=Editor.Content.size())
        			{
        				Editor.Content.setSize(Editor.Content.size()+10);
        			}
        			Editor.Content.setElementAt("|",getStartOffset()+start);
        		}
        		
        		if(Character.isDigit(StringToTransform.charAt(start)))
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


        
    } 
} 
