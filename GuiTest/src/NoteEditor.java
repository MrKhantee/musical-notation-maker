import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Robot;
import javax.swing.ImageIcon;
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

import javax.swing.ButtonGroup;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;




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
		setBounds(100, 100, 674, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textPane = new JTextPane();
		textPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane.setBounds(10, 134, 638, 279);
		contentPane.add(textPane);
		textPane.setEditorKit(new StyledEditorKit() { 
            public ViewFactory getViewFactory() { 
                return new NewViewFactory(); 

            } 
        }); 
		
        StyledDocument doc = (StyledDocument) textPane.getDocument(); 
        Style style=new StyleContext().new NamedStyle();
        StyleConstants.setSpaceAbove(style, 10);
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
		
		JToggleButton tglbtnth = new JToggleButton("4th");
		tglbtnth.setSelected(true);
		tglbtnth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Note",new Integer(0)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedNoteButton!=null)
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
		tglbtnth.setBounds(111, 10, 70, 23);
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
					if(selectedNoteButton!=null)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton.setBounds(218, 10, 70, 23);
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
					if(selectedNoteButton!=null)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_1.setBounds(363, 10, 70, 23);
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
					if(selectedNoteButton!=null)
					{
						selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedNoteButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_2.setBounds(460, 10, 70, 23);
		contentPane.add(tglbtnNewToggleButton_2);
		
		tglbtnHtune = new JToggleButton("HTune");
		tglbtnHtune.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonGroup_1.add(tglbtnHtune);
		tglbtnHtune.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(1)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnHtune.setBounds(111, 43, 119, 23);
		contentPane.add(tglbtnHtune);
		
		tglbtnNewToggleButton_3 = new JToggleButton("HHTune");
		tglbtnNewToggleButton_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonGroup_1.add(tglbtnNewToggleButton_3);
		tglbtnNewToggleButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(2)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_3.setActionCommand("HHTune");
		tglbtnNewToggleButton_3.setBounds(268, 43, 119, 23);
		contentPane.add(tglbtnNewToggleButton_3);
		
		tglbtnNewToggleButton_4 = new JToggleButton("HHHTune");
		tglbtnNewToggleButton_4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonGroup_1.add(tglbtnNewToggleButton_4);
		tglbtnNewToggleButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(3)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_4.setBounds(411, 43, 119, 23);
		contentPane.add(tglbtnNewToggleButton_4);
		
		tglbtnNewToggleButton_5 = new JToggleButton("LTune");
		tglbtnNewToggleButton_5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonGroup_1.add(tglbtnNewToggleButton_5);
		tglbtnNewToggleButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(-1)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_5.setBounds(111, 76, 119, 23);
		contentPane.add(tglbtnNewToggleButton_5);
		
		tglbtnNewToggleButton_6 = new JToggleButton("LLTune");
		tglbtnNewToggleButton_6.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonGroup_1.add(tglbtnNewToggleButton_6);
		tglbtnNewToggleButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(-2)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_6.setBounds(268, 80, 119, 23);
		contentPane.add(tglbtnNewToggleButton_6);
		
		tglbtnNewToggleButton_7 = new JToggleButton("LLLTune");
		tglbtnNewToggleButton_7.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonGroup_1.add(tglbtnNewToggleButton_7);
		tglbtnNewToggleButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton toggle = (JToggleButton)e.getSource();
				if(toggle.isSelected())
				{
					attr.addAttribute("Tune",new Integer(-3)); 
					toggle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					if(selectedTuneButton!=null)
					{
						selectedTuneButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					textPane.setCharacterAttributes(attr,false);
					selectedTuneButton=toggle;
				}
			}
		});
		tglbtnNewToggleButton_7.setBounds(411, 80, 119, 23);
		contentPane.add(tglbtnNewToggleButton_7);
		
		JToggleButton tglbtnSave = new JToggleButton("Save");
		tglbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Rectangle textRec=textPane.getBounds();
					Point textRecXY=new Point(textRec.x,textRec.y);
					Point textRecWH=new Point(textRec.width,textRec.height);
					SwingUtilities.convertPointToScreen(textRecXY,contentPane);
					textRec.setBounds(textRecXY.x,textRecXY.y,textRecWH.x,textRecWH.y);
					BufferedImage bi = new Robot().createScreenCapture(textRec);
		            ImageIO.write(bi, "jpg", new File("t.jpg"));
				}
				catch(Exception ex){}
				
			}
		});
		tglbtnSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tglbtnSave.setBounds(10, 43, 91, 23);
		contentPane.add(tglbtnSave);
		

		
		

		
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
    public View create(Element elem) { 
        String kind = elem.getName(); 
        if (kind != null) { 
            if (kind.equals(AbstractDocument.ContentElementName)) { 

                return new MyLabelView(elem); 
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

 
    public MyLabelView(Element elem) { 
        super(elem); 
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
            	if(Note==null)  lineCount=0;
            	else lineCount=Note.intValue()-1;
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
    } 
} 
