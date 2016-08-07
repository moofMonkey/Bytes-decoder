package ru.XedosAndMoofMonkey.decoder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class GUI extends JFrame {
	private static final long serialVersionUID = -2308981727233366140L;

	JTextField textField;
	JTextField textField1;
	JButton jButton;

	ClassPool cp = ClassPool.getDefault();

	public void render() {
		jButton = new JButton();
		textField = new JTextField();
		textField1 = new JTextField();

		jButton = new JButton("Decode");
		textField.setBounds(20, 20, 450, 25);
		textField1.setBounds(20, 55, 450, 25);
		textField1.setEditable(false);
		jButton.setBounds(137, 110, 210, 25);
		jButton.addActionListener(e -> {
			try {
				CtClass cl = cp.makeClass("ru.XedosAndMoofMonkey.decoder.decoded" + NameGen.get());

				String origText = textField.getText();

				if (!(origText.startsWith("[") || origText.endsWith("]"))
						&& !(origText.startsWith("new byte[] {") || origText.endsWith("}")))
					throw new Throwable("Кривожопый юзер");

				if (origText.startsWith("["))
					origText = "new byte[] { " + origText.substring(1, origText.length());
				if (origText.endsWith("]"))
					origText = origText.substring(0, origText.length() - 1) + " }";

				cl.addMethod(
						CtMethod.make("public String toString() { return new String(" + origText + "); }", cl));
				cl.setSuperclass(cp.get("java.lang.Object"));

				textField1.setText(cl.toClass().newInstance().toString());
			} catch (Throwable t) {
				t.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unhandled exception: ПИЗДЮК, НЕПРАВИЛЬНЫЕ БАЙТЫ НАХУЙ!");
			}
		});

		setTitle("StringToByte[] Decoder by Xedos.cpp && moofMonkey.java");
		setDefaultCloseOperation(3);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(500, 160);

		add(jButton);
		add(textField);
		add(textField1);
		setVisible(true);
	}
}
