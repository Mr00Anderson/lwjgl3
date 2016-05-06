/* 
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.generator.util;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TemplateFormatter {

	final JRadioButton radioConst;
	final JRadioButton radioFunc;
	final JRadioButton radioDoc;

	final JTextField prefix;
	final JCheckBox  prefixTypes;

	private final JTextArea input;
	private final JTextArea output;

	private TemplateFormatter() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		JFrame frame = new JFrame("Template Formatter");
		frame.getContentPane().setLayout(new BorderLayout(4, 4));

		// Settings

		radioConst = new JRadioButton("Constants", true);
		radioFunc = new JRadioButton("Functions");
		radioDoc = new JRadioButton("Documentation");

		prefix = new JTextField("GL", 4);
		prefixTypes = new JCheckBox("Prefix types", true);

		ButtonGroup radioGroup = new ButtonGroup();

		radioGroup.add(radioConst);
		radioGroup.add(radioFunc);
		radioGroup.add(radioDoc);

		JPanel radioPane = new JPanel(new FlowLayout());
		radioPane.add(radioConst);
		radioPane.add(radioFunc);
		radioPane.add(radioDoc);
		radioPane.add(new JLabel("Prefix:"));
		radioPane.add(prefix);
		radioPane.add(prefixTypes);

		frame.getContentPane().add(radioPane, BorderLayout.NORTH);

		// Text areas

		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);

		input = new JTextArea(32, 64);
		input.setFont(font);
		input.setLineWrap(false);
		input.setTabSize(4);

		output = new JTextArea(32, 64);
		output.setFont(font);
		output.setLineWrap(false);
		output.setTabSize(4);
		output.setBackground(Color.LIGHT_GRAY);
		output.setEditable(false);

		setup();

		frame.getContentPane().add(new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			true,
			new JScrollPane(input),
			new JScrollPane(output)
		), BorderLayout.CENTER);

		// Config and show

		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			frame.setIconImages(Arrays.asList(new Image[] {
				ImageIO.read(cl.getResource("lwjgl16.png")),
				ImageIO.read(cl.getResource("lwjgl32.png"))
			}));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new TemplateFormatter();
	}

	private void setup() {
		input.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				format();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				format();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				format();
			}
		});

		ActionListener settingsAction = e -> format();

		radioConst.addActionListener(settingsAction);
		radioFunc.addActionListener(settingsAction);
		radioDoc.addActionListener(settingsAction);

		prefix.addActionListener(settingsAction);
		prefixTypes.addActionListener(settingsAction);
	}

	private void format() {
		String inputText = input.getText().trim();
		if ( inputText.isEmpty() ) {
			output.setBackground(Color.LIGHT_GRAY);
			output.setText("");
			return;
		}

		try {
			String outputText;
			if ( !radioDoc.isSelected() ) {
				outputText = radioConst.isSelected()
					? formatConstants(inputText, prefix.getText())
					: formatFunctions(inputText, prefix.getText(), prefixTypes.isSelected());

				// Try to automatically detect the input type
				if ( outputText.isEmpty() ) {
					outputText = radioConst.isSelected()
						? formatFunctions(inputText, prefix.getText(), prefixTypes.isSelected())
						: formatConstants(inputText, prefix.getText());

					// Got it, flip the selection
					if ( !outputText.isEmpty() )
						(radioConst.isSelected() ? radioFunc : radioConst).setSelected(true);
					else
						outputText = formatDocumentation(inputText);
				}
			} else {
				outputText = formatDocumentation(inputText);
			}

			output.setBackground(Color.WHITE);
			output.setText(outputText);

			// Copy output to clipboard
			//final StringSelection copyData = new StringSelection(outputText);
			//Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyData, copyData);
		} catch (Exception e) {
			e.printStackTrace();

			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));

			output.setBackground(Color.ORANGE);
			output.setText("** ERROR **\n\n" + writer.toString());
		}
	}

	private static String strip(String token, String prefix) {
		return !prefix.isEmpty() && prefix.length() < token.length() && token.substring(0, prefix.length()).equalsIgnoreCase(prefix)
			? token.substring(prefix.length())
			: token;
	}

	// ---[ CONSTANT FORMATTING ]----

	private static String formatConstants(String input, String prefix) {
		String constants = formatConstantsGL(input, prefix);
		if ( constants.isEmpty() )
			constants = formatConstantsEnum(input, prefix);

		return constants;
	}

	private static final String COMMENT = "(?:/[*].+?[*]/|//[^\n\r]*)?";

	private static final String DESCRIPTION    = "(?:(?:/[*]+\\s*(.+?)\\s*[*]+/)|(?:([^:]+):))";
	private static final String DEFINE         = "(?:#define\\s+)?";
	private static final String CONSTANT_VALUE = "[0-9A-Za-z_]+|\\([^)]+\\)|[-x\\p{XDigit}]+U?L?";

	private static final Pattern BLOCK_PATTERN = Pattern.compile(
		DESCRIPTION + "\\s+((?:\\s*" + DEFINE + "[0-9A-Za-z_]+\\s+(?:" + CONSTANT_VALUE + ")[ \t]*" + COMMENT + "\\s*$)+)\\s*",
		Pattern.MULTILINE | Pattern.DOTALL
	);

	private static final Pattern COMMENT_CLEANUP = Pattern.compile("[\n\r]*(?:\\s*[*])?\\s+", Pattern.MULTILINE);
	private static final Pattern CODE_CLEANUP    = Pattern.compile("<([^>]+)>");
	private static final Pattern TOKEN_SPLIT     = Pattern.compile("(?<!@code)\\s+"); // Don't split code fragments

	private static final Pattern CONSTANT_PATTERN = Pattern.compile(
		DEFINE + "([0-9A-Za-z_]+)\\s+(" + CONSTANT_VALUE + ")[ \t]*" + COMMENT,
		Pattern.DOTALL
	);

	private static String formatConstantsGL(String input, String prefix) {
		StringBuilder builder = new StringBuilder(input.length());

		Matcher blockMatcher = BLOCK_PATTERN.matcher(input);
		int blockCount = 0;
		while ( blockMatcher.find() ) {
			if ( 0 < blockCount++ ) builder.append('\n');

			String description = blockMatcher.group(1);
			if ( description == null )
				description = blockMatcher.group(2) + '.';

			description =
				CODE_CLEANUP.matcher(
					COMMENT_CLEANUP.matcher(description).replaceAll(" ").trim()
				).replaceAll("{@code $1}");

			builder.append("\tIntConstant(\n");
			if ( description.length() <= 160 - (4 + 4 + 2 + 1) ) {
				builder.append("\t\t\"");
				builder.append(description);
				builder.append('\"');
			} else {
				builder.append("\t\t\"\"\"\n");
				builder.append("\t\t");

				String[] tokens = TOKEN_SPLIT.split(description);
				int MAX_LINE_LENGTH = 160 - (4 + 4);

				int lineLength = 0;
				for ( String token : tokens ) {
					lineLength += token.length();
					if ( token.length() < lineLength ) {
						if ( MAX_LINE_LENGTH < 1 + lineLength ) {
							builder.append("\n\t\t");
							lineLength = token.length();
						} else {
							builder.append(' ');
							lineLength++;
						}
					}

					builder.append(token);
				}

				builder.append("\n\t\t\"\"\"");
			}
			builder.append(",\n\n");

			Matcher constantMatcher = CONSTANT_PATTERN.matcher(blockMatcher.group(3));
			int constCount = 0;
			while ( constantMatcher.find() ) {
				if ( 0 < constCount++ ) builder.append(",\n");

				builder.append("\t\t\"");
				builder.append(strip(constantMatcher.group(1), prefix + '_'));

				String value = constantMatcher.group(2);

				try {
					String intValue = value.endsWith("L") ? value.substring(0, value.length() - (value.endsWith("UL") ? 2 : 1)) : value;

					validateInteger(intValue);
					if ( intValue.startsWith("0x") ) {
						builder.append("\"..");
						builder.append(intValue);
					} else {
						builder.append("\"..\"");
						builder.append(intValue);
						builder.append("\"");
					}
				} catch (NumberFormatException e) {
					builder.append("\"..\"");
					builder.append(value.charAt(0) == '(' ? value.substring(1, value.length() - 1) : value);
					builder.append("\"");
				}
			}
			builder.append("\n\t)\n");
		}

		return builder.toString();
	}

	private static final Pattern ENUM_PATTERN       = Pattern.compile("typedef\\s+enum\\s+(\\w+)\\s*\\{([^}]+)}\\s*\\w+;");
	private static final Pattern ENUM_VALUE_PATTERN = Pattern.compile(
		"\\s*(\\w+)\\s*=\\s*(" + CONSTANT_VALUE + ")\\s*,?"
	);

	private static String formatConstantsEnum(String input, String prefix) {
		StringBuilder builder = new StringBuilder(input.length());

		Matcher enumMatcher = ENUM_PATTERN.matcher(input);
		int blockCount = 0;
		while ( enumMatcher.find() ) {
			if ( 0 < blockCount++ ) builder.append('\n');

			builder
				.append("\tEnumConstant(\n")
				.append("\t\t\"")
				.append(enumMatcher.group(1))
				.append("\",\n\n");

			Matcher constantMatcher = ENUM_VALUE_PATTERN.matcher(enumMatcher.group(2));
			int constCount = 0;
			String lastValue = null;
			while ( constantMatcher.find() ) {
				if ( 0 < constCount++ ) builder.append(",\n");

				builder
					.append("\t\t\"")
					.append(strip(constantMatcher.group(1), prefix + '_'))
					.append("\".enum");

				String value = constantMatcher.group(2);

				try {
					String intValue = value.endsWith("L") ? value.substring(0, value.length() - (value.endsWith("UL") ? 2 : 1)) : value;

					int i = validateInteger(intValue).intValue();
					if ( lastValue != null && (Long.decode(lastValue).intValue() + 1) == i ) {
						builder.append("(\"\"");
					} else {
						if ( intValue.startsWith("0x") ) {
							builder
								.append("(\"\", ")
								.append(intValue);
						} else {
							builder
								.append("Expr(\"\", \"")
								.append(intValue)
								.append("\"");
						}
					}
					lastValue = intValue;
				} catch (NumberFormatException e) {
					builder
						.append("Expr(\"\", \"")
						.append(value.charAt(0) == '(' ? value.substring(1, value.length() - 1) : value)
						.append("\"");
				}
				builder.append(")");
			}
			builder.append("\n\t)\n");
		}

		return builder.toString();
	}

	private static Long validateInteger(String intValue) {
		Long l = Long.decode(intValue);
		if ( (1L << 32) - 1 < l )
			throw new NumberFormatException("long value");

		return l;
	}

	// ---[ FUNCTION FORMATTING ]----

	private static final Pattern TYPE_PATTERN = Pattern.compile(
		"(?:const\\s+)?" + // const
			"(?:(?:un)signed\\s+)?" + // (un)signed
			"[0-9a-zA-Z_]++" + // type
			"(?:\\s+const)?(?:\\s*[*]+\\s*|\\s+)" + // pointer. This is a little funny because we can have whitespace on either side of *
			"(?:/[*]\\s*)?" + // name may be wrapped in comments /*
			"[0-9a-zA-Z_]+" + // function or parameter name
			"(?:\\s*[*]/)?"
	);

	private static final Pattern FUNCTION_PATTERN = Pattern.compile(
		TYPE_PATTERN + // return type + function name
			"\\s*[(]\\s*" + // opening parenthesis
			"((?:void)?(?:(?:\\s*,)?\\s*" + TYPE_PATTERN + ")*)" + // void or parameter list
			"\\s*[)]", // closing parenthesis
		Pattern.MULTILINE
	);

	// Same as TYPE_PATTERN, with capturing groups and without the whitespace stuff (we've already verified correct syntax)
	private static final Pattern PARAM_PATTERN = Pattern.compile(
		"(const\\s+)?" +
			"((?:un)?signed\\s+)?" +
			"([0-9a-zA-Z_]++)" +
			"(\\s+const)?\\s*([*]+)?\\s*" +
			"(?:/[*]\\s*)?" +
			"([0-9a-zA-Z_]+)" +
			"(?:\\s*[*]/)?",
		Pattern.MULTILINE
	);

	private static void formatType(Matcher paramMatcher, StringBuilder builder, String prefix) {
		// (un)signed
		if ( paramMatcher.group(2) != null )
			builder.append(paramMatcher.group(2).trim() + "_");
		// type
		String type = paramMatcher.group(3);

		if ( !prefix.isEmpty() && !type.substring(0, prefix.length()).equalsIgnoreCase(prefix) )
			builder.append(prefix);
		builder.append(type);
		if ( "unsigned".equals(type) || "signed".equals(type) )
			builder.append("_int");
	}

	private static String formatFunctions(String input, String prefix, boolean prefixTypes) {
		StringBuilder builder = new StringBuilder(input.length());

		Matcher funcMatcher = FUNCTION_PATTERN.matcher(input);

		int funcCount = 0;
		while ( funcMatcher.find() ) {
			if ( 0 < funcCount++ ) builder.append("\n\n");

			String function = funcMatcher.group();

			Matcher paramMatcher = PARAM_PATTERN.matcher(function);

			int paramCount = -1;
			while ( paramMatcher.find() ) {
				if ( paramCount == -1 ) {
					// Return type + function name
					builder.append('\t');
					// const
					if ( paramMatcher.group(1) != null || paramMatcher.group(4) != null )
						builder.append("(const..");
					// type
					formatType(paramMatcher, builder, prefixTypes ? prefix : "");
					// pointer
					if ( paramMatcher.group(5) != null )
						writerPointer(builder, paramMatcher);
					if ( paramMatcher.group(1) != null )
						builder.append(')');
					builder.append("(\n");
					builder.append("\t\t\"");
					builder.append(strip(paramMatcher.group(6), prefix));
					builder.append("\",\n");
					builder.append("\t\t\"\"");

					paramCount = 0;
					if ( "void".equals(funcMatcher.group(1)) )
						break;
				} else {
					// Normal parameter
					builder.append(",\n");
					if ( paramCount++ == 0 )
						builder.append('\n');

					builder.append("\t\t");
					// const
					if ( paramMatcher.group(1) != null || paramMatcher.group(4) != null ) // const
						builder.append("const..");
					// type
					formatType(paramMatcher, builder, prefixTypes ? prefix : "");
					// pointer
					if ( paramMatcher.group(5) != null ) {
						writerPointer(builder, paramMatcher);
						builder.append(
							paramMatcher.group(1) != null || paramMatcher.group(4) != null // const
								? ".IN(\""
								: ".OUT(\""
						);
					} else
						builder.append(".IN(\"");
					builder.append(paramMatcher.group(6));
					builder.append("\", \"\")");
				}
			}

			builder.append("\n\t)");
		}

		return builder.toString();
	}

	private static void writerPointer(StringBuilder builder, Matcher paramMatcher) {
		builder.append('_');
		for ( int i = 0; i < paramMatcher.group(5).length(); i++ )
			builder.append('p');
	}

	// ---[ DOCUMENTATION FORMATTING ]----

	private static final Pattern PARAGRAPH  = Pattern.compile("\\n(\\s*\\n)+\\s*");
	private static final Pattern WHITESPACE = Pattern.compile("\\s+|$");

	private static String formatDocumentation(String input) {
		StringBuilder builder = new StringBuilder(input.length());

		String[] paragraphs = PARAGRAPH.split(input);
		for ( String paragraph : paragraphs ) {
			if ( builder.length() != 0 )
				builder.append("\n\n");

			Matcher matcher = WHITESPACE.matcher(paragraph);

			int lineLen = 8;
			int lastMatch = 0;
			while ( matcher.find() ) {
				int wordLen = matcher.start() - lastMatch;

				if ( 160 < lineLen + wordLen + 1 ) {
					builder.append("\n");
					lineLen = 8;
				}

				builder.append(lineLen == 8 ? "\t\t" : " ");
				builder.append(paragraph, lastMatch, matcher.start());

				lineLen += wordLen + 1;
				lastMatch = matcher.end();
			}
		}

		return builder.toString();
	}

}