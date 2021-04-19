	import java.awt.EventQueue;
	import javax.swing.BorderFactory;
	import javax.swing.ButtonGroup;
	import javax.swing.JFrame;
	import java.awt.Font;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import java.awt.Color;
	import java.awt.Dimension;
	import javax.swing.SwingConstants;
	import javax.swing.JPanel;
	import java.awt.FlowLayout;
	import javax.swing.JRadioButton;
	import javax.swing.JTextField;
	import javax.swing.JCheckBox;
	import java.awt.GridLayout;
	import javax.swing.JButton;
	import javax.swing.border.Border;
	import javax.swing.border.CompoundBorder;
	import javax.swing.border.EmptyBorder;
	import javax.swing.border.LineBorder;
	
	class KarnaughMap implements ActionListener {
	
		private JFrame frmKarnaughMap;
		private JPanel panTruthTable = new JPanel();
		private JPanel panKMap = new JPanel();
		JLabel lblFunction = new JLabel("F(A,B) = AB");
	
		JButton[] listTruthButtons;
		JButton[][] listKMapButtons;
	
		private String[] variable2 = new String[] { "00", "01", "10", "11" };
		private String[] variable3 = new String[] { "000", "001", "010", "011", "100", "101", "110", "111" };
		private String[] variable4 = new String[] { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
				"1001", "1010", "1011", "1100", "1101", "1110", "1111" };
	
		private String[][] variable2KMap = new String[][] { { "00", "10" }, { "01", "11" } };
	
		private String[][] variable3KMap = new String[][] { { "000", "010", "110", "100" },
				{ "001", "011", "111", "101" } };
	
		private String[][] variable4KMap = new String[][] { { "0000", "0100", "1100", "1000" },
				{ "0001", "0101", "1101", "1001" }, { "0011", "0111", "1111", "1011", },
				{ "0010", "0110", "1110", "1010" } };
	
		private String[] truth2 = new String[] { "0", "0", "0", "0" };
		private String[] truth3 = new String[] { "0", "0", "0", "0", "0", "0", "0", "0" };
		private String[] truth4 = new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
				"0" };
	
		private String[][] kmap2 = new String[][] { { "0", "0" }, { "0", "0" } };
		private String[][] kmap3 = new String[][] { { "0", "0", "0", "0" }, { "0", "0", "0", "0" } };
		private String[][] kmap4 = new String[][] { { "0", "0", "0", "0" }, { "0", "0", "0", "0" }, { "0", "0", "0", "0", },
				{ "0", "0", "0", "0" } };
	
		private String[] VARIABLE_NAME = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N"};
	
		private int VARIABLE;
		private boolean ALLOW_IGNORANCE = false;
	
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						KarnaughMap window = new KarnaughMap();
						window.frmKarnaughMap.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	
			public KarnaughMap() {
			initialize();
		}
	
	
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			String actionType = actionCommand.substring(0, 8);
	
			// this is the button index
			int actionIndex;
	
			try {
				System.out.println("Selected: " + actionCommand);
				if (actionType.equals("variable")) {
					VARIABLE = Integer.parseInt(actionCommand.substring(8));
	
					switch (VARIABLE) {
					case 2:
						updateTruthTablePanel(variable2, truth2);
						updateKMapPanel(variable2KMap, kmap2);
						
						simplify(variable2, truth2, variable2KMap, kmap2);
						break;
					case 3:
						updateTruthTablePanel(variable3, truth3);
						updateKMapPanel(variable3KMap, kmap3);
						
						simplify(variable3, truth3, variable3KMap, kmap3);
						break;
					case 4:
						updateTruthTablePanel(variable4, truth4);
						updateKMapPanel(variable4KMap, kmap4);
						
						simplify(variable4, truth4, variable4KMap, kmap4);
						break;
					}
				} else if (actionType.equals("btntruth")) {
					actionIndex = Integer.parseInt(actionCommand.substring(8));
					String[] mapping;
					int row = 0, column = 0;
					
					switch (VARIABLE) {
					case 2:
						mapping = getRowOrColumn(variable2, actionIndex, variable2KMap, row, column, 1);					
						row = Integer.parseInt(mapping[0]);
						column = Integer.parseInt(mapping[1]);
						
						updateValueInTable(variable2, truth2, actionIndex, variable2KMap, kmap2, row, column, 1);
						updateTruthTablePanel(variable2, truth2);
						updateKMapPanel(variable2KMap, kmap2);
						
						simplify(variable2, truth2, variable2KMap, kmap2);
						break;
					case 3:
						mapping = getRowOrColumn(variable3, actionIndex, variable3KMap, row, column, 1);					
						row = Integer.parseInt(mapping[0]);
						column = Integer.parseInt(mapping[1]);
						
						updateValueInTable(variable3, truth3, actionIndex, variable3KMap, kmap3, row, column, 1);
						updateTruthTablePanel(variable3, truth3);
						updateKMapPanel(variable3KMap, kmap3);
						
						simplify(variable3, truth3, variable3KMap, kmap3);
						break;
					case 4:
						mapping = getRowOrColumn(variable4, actionIndex, variable4KMap, row, column, 1);					
						row = Integer.parseInt(mapping[0]);
						column = Integer.parseInt(mapping[1]);
						
						updateValueInTable(variable4, truth4, actionIndex, variable4KMap, kmap4, row, column, 1);
						updateTruthTablePanel(variable4, truth4);
						updateKMapPanel(variable4KMap, kmap4);
						
						// simplify
						simplify(variable4, truth4, variable4KMap, kmap4);
						break;
					}
	
				} else if (actionType.equals("btnkamap")) {
					String cellIndex = actionCommand.substring(8);
					String[] mapping;
					int row = Integer.parseInt(cellIndex.substring(0, 1));
					int column = Integer.parseInt(cellIndex.substring(2));
					int index = 0;
					switch (VARIABLE) {
					case 2:
						mapping = getRowOrColumn(variable2, index, variable2KMap, row, column, 2);					
						index = Integer.parseInt(mapping[0]);
						
						updateValueInTable(variable2, truth2, index, variable2KMap, kmap2, row, column, 2);
						updateTruthTablePanel(variable2, truth2);
						updateKMapPanel(variable2KMap, kmap2);
						
						simplify(variable2, truth2, variable2KMap, kmap2);
						break;
					case 3:
						mapping = getRowOrColumn(variable3, index, variable3KMap, row, column, 2);					
						index = Integer.parseInt(mapping[0]);
						
						updateValueInTable(variable3, truth3, index, variable3KMap, kmap3, row, column, 2);
						updateTruthTablePanel(variable3, truth3);
						updateKMapPanel(variable3KMap, kmap3);
						
						// simplify
						simplify(variable3, truth3, variable3KMap, kmap3);
						break;
					case 4:
						mapping = getRowOrColumn(variable4, index, variable4KMap, row, column, 2);					
						index = Integer.parseInt(mapping[0]);
						
						updateValueInTable(variable4, truth4, index, variable4KMap, kmap4, row, column, 2);
						updateTruthTablePanel(variable4, truth4);
						updateKMapPanel(variable4KMap, kmap4);
						
						// simplify
						simplify(variable4, truth4, variable4KMap, kmap4);
						break;
					}
				}
				// Don't care
				else if (actionType.equals("allowIgn")) {
					if (ALLOW_IGNORANCE == false) {
						ALLOW_IGNORANCE = true;
					} else {
						ALLOW_IGNORANCE = false;
					}
				}
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(frmKarnaughMap, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void simplify (String[] aValueTruthTable, String[] theTruthTable, String[][] aValueKMapTable, String[][] theKMapTable)
		{
			String functionOutput = "";
			int i;
			boolean isAllZeros = true, isAllOnes = true, isAllIgnorances = true;
			
			if (VARIABLE == 2)
				functionOutput = "F(" + VARIABLE_NAME[0] + ", " + VARIABLE_NAME[1] + ") = "; 
			else if (VARIABLE == 3)
				functionOutput = "F(" + VARIABLE_NAME[0] + ", " + VARIABLE_NAME[1] + ", " + VARIABLE_NAME[2]  + ") = "; 
			else if (VARIABLE == 4)
				functionOutput = "F(" + VARIABLE_NAME[0] + ", " + VARIABLE_NAME[1] + ", " + VARIABLE_NAME[2] + ", " + VARIABLE_NAME[3] + ") = "; 
			
			
			// check if all 0s or all 1s
			for (i = 0; i < theTruthTable.length; i++)
			{
				if (theTruthTable[i].equals("1"))
				{
					isAllZeros = false;
				}
				if (theTruthTable[i].equals("0"))
				{
					isAllOnes = false;
				}
				if (!theTruthTable[i].equals("x"))
				{
					isAllIgnorances = false;
				}
			}
			
			if (isAllZeros || isAllIgnorances)
			{
				functionOutput += "0";
			}
			else if (isAllOnes || isAllIgnorances)
			{
				functionOutput += "1";
			}	
			// do the simplification here
			else
			{
				Simplifier simplifier = new Simplifier();
				String functionSimplify = "";
				
				int[] arrMinTerms = getSubArray(theTruthTable, 1);
				int[] arrIgnorances = getSubArray(theTruthTable, 2);
				
				printArray(arrMinTerms);
				printArray(arrIgnorances);
				
				//functionOutput += getFunctionOutput(aValueKMapTable, theKMapTable);
				
				functionSimplify = simplifier.getSimplifier(arrMinTerms, arrIgnorances, VARIABLE);			
				functionOutput += functionSimplify;
			}
			
			lblFunction.setText(functionOutput);		
		}
		
		
		private void printArray (int[] anArray)
		{
			for (int i = 0; i < anArray.length; i++)
			{
				System.out.print(anArray[i] + " ");
			}
			System.out.println();
		}
		
		/**
		 * Initialize the contents of the frame.
		 */
	 	private void initialize() {
	
			frmKarnaughMap = new JFrame();
			frmKarnaughMap.setTitle("Karnaugh Map");
			frmKarnaughMap.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			frmKarnaughMap.setBounds(100, 100, 510, 450);
			frmKarnaughMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmKarnaughMap.setResizable(false);
			frmKarnaughMap.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	
			ButtonGroup variableButtonGroup = new ButtonGroup();
	
			JPanel panTop = new JPanel();
			panTop.setBorder(new EmptyBorder(0, 10, 10, 0));
			frmKarnaughMap.getContentPane().add(panTop);
			panTop.setPreferredSize(new Dimension(500, 50));
			panTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	
			JLabel lblKarnaughMap = new JLabel("Karnaugh Map");
			lblKarnaughMap.setHorizontalAlignment(SwingConstants.CENTER);
			lblKarnaughMap.setPreferredSize(new Dimension(500, 25));
			lblKarnaughMap.setFont(new Font("Times New Roman", Font.BOLD, 20));
			panTop.add(lblKarnaughMap);
	
			JRadioButton rdbtn2Variable = new JRadioButton("Two Variables");
			rdbtn2Variable.setHorizontalAlignment(SwingConstants.LEFT);
			rdbtn2Variable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			rdbtn2Variable.setActionCommand("variable2");
			rdbtn2Variable.addActionListener(this);
			panTop.add(rdbtn2Variable);
	
			JRadioButton rdbtn3Variable = new JRadioButton("Three Variables");
			rdbtn3Variable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			rdbtn3Variable.setActionCommand("variable3");
			rdbtn3Variable.addActionListener(this);
			panTop.add(rdbtn3Variable);
	
			JRadioButton rdbtn4Variable = new JRadioButton("Four Variables");
			rdbtn4Variable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			rdbtn4Variable.setActionCommand("variable4");
			rdbtn4Variable.addActionListener(this);
			panTop.add(rdbtn4Variable);
	
			variableButtonGroup.add(rdbtn2Variable);
			variableButtonGroup.add(rdbtn3Variable);
			variableButtonGroup.add(rdbtn4Variable);
	
			JCheckBox chckbxIgnorenceAllow = new JCheckBox("Allow Ignorence");
			chckbxIgnorenceAllow.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			chckbxIgnorenceAllow.setActionCommand("allowIgn");
			chckbxIgnorenceAllow.addActionListener(this);
			panTop.add(chckbxIgnorenceAllow);
	
			JPanel panResult = new JPanel();
			panResult.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			panResult.setPreferredSize(new Dimension(500, 30));
			frmKarnaughMap.getContentPane().add(panResult);
	
			lblFunction.setBorder(new EmptyBorder(0, 10, 10, 0));
			lblFunction.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblFunction.setPreferredSize(new Dimension(500, 20));
			panResult.add(lblFunction);
	
			JPanel panInput = new JPanel();
			panInput.setBorder(new EmptyBorder(0, 10, 10, 0));
			panInput.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			panInput.setPreferredSize(new Dimension(500, 350));
			frmKarnaughMap.getContentPane().add(panInput);
			panInput.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
			panTruthTable.setBorder(new EmptyBorder(0, 0, 0, 0));
	
			panTruthTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			panTruthTable.setPreferredSize(new Dimension(200, 310));
			panInput.add(panTruthTable);
	
			panKMap.setPreferredSize(new Dimension(290, 310));
			panKMap.setFont(new Font("Times New Roman", Font.PLAIN, 12));
	
			panInput.add(panKMap);
	
		}
	
		private void updateValueInTable(String[] aValueTruthTable, String[] theTruthTable, int anIndex,
				String[][] aValueKMapTable, String[][] theKMapTable, int row, int column, int direction) {
	
			if (ALLOW_IGNORANCE == false) {
				// update the truth table
				// 1 <==> 0, 0 <==> 1
				if (theTruthTable[anIndex].equals("0") && theKMapTable[row][column].equals("0") || theTruthTable[anIndex].isEmpty()
						 && theKMapTable[row][column].isEmpty()) {
	
					synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
							column, "1", direction);
	
				} else {
	
					synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
							column, "0", direction);
				}
	
			} else {
				// 0 ==> 1
				if (theTruthTable[anIndex].equals("0") && theKMapTable[row][column].equals("0") || theTruthTable[anIndex].isEmpty()
						 && theKMapTable[row][column].isEmpty()) {
					
					synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
							column, "1", direction);
	
				} else if (theTruthTable[anIndex].equals("1") && theKMapTable[row][column].equals("1")) {
					// 1 ==> x
					synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
							column, "x", direction);
				} else {
					// x ==> 0
					synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
							column, "0", direction);
	
				}
			}
		}
	
		private void synchonizeTwoTables(String[] aValueTruthTable, String[] theTruthTable, int anIndex,
				String[][] aValueKMapTable, String[][] theKMapTable, int row, int column, String theNewValue,
				int direction) {
			int i, j;
			String theValue;
			boolean isDone = false;
	
			theValue = aValueTruthTable[anIndex];
			if (direction == 2) {
				theValue = aValueKMapTable[row][column];
			}
	
			// synchronize from truth table to kmap
			for (i = 0; i < aValueKMapTable.length; i++) {
				for (j = 0; j < aValueKMapTable[i].length; j++) {
					if (theValue.equalsIgnoreCase(aValueKMapTable[i][j])) {
						// set value for theKMapTable
						theKMapTable[i][j] = theNewValue;
						isDone = true;
						break;
					}
				}
	
				if (isDone == true)
					break;
			}
	
			// synchronize from kmap to truth table
			for (i = 0; i < aValueTruthTable.length; i++) {
				if (theValue.equalsIgnoreCase(aValueTruthTable[i])) {
					theTruthTable[i] = theNewValue;
					break;
				}
			}
			
			//print out the matrix
			for (i = 0; i < theKMapTable.length; i++) {
				for (j = 0; j < theKMapTable[i].length; j++) {
					System.out.print(theKMapTable[i][j] + " ");
				}
				System.out.println();
				
			}
			
			// print out the truth table
			for (i = 0; i < aValueTruthTable.length; i++) {
				System.out.print(theTruthTable[i] + " ");
			}
			System.out.println();
		}
	
		private void updateTruthTablePanel(String[] valueTable, String[] truthTable) {
			int rows = 0;
			int i = 0, j = 0;
			String _rowValue = "", _columnValue = "";
	
			rows = valueTable.length;
	
			panTruthTable.removeAll();
			panTruthTable.revalidate();
			panTruthTable.repaint();
			panTruthTable.setLayout(new GridLayout(rows, valueTable[0].length(), 5, 5));
	
			listTruthButtons = new JButton[rows];
	
			for (i = 0; i < rows; i++) {
				_rowValue = valueTable[i];
	
				for (j = 0; j < _rowValue.length(); j++) {
					_columnValue = "" + _rowValue.charAt(j);
	
					JTextField aTextFields;
					aTextFields = new JTextField(_columnValue);
					aTextFields.setPreferredSize(new Dimension(15, 15));
					aTextFields.setMaximumSize(new Dimension(15, 15));
					aTextFields.setMinimumSize(new Dimension(15, 15));
					aTextFields.setEditable(false);
					aTextFields.setBorder(BorderFactory.createEmptyBorder());
					aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
					aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
	
					panTruthTable.add(aTextFields);
				}
				// for the last columns
				String buttonText = "0";
	
				if (truthTable[i].equals("1")) {
					buttonText = "1";
				} else if (truthTable[i].equals("x")) {
					buttonText = "x";
				} else {
					buttonText = "0";
				}
	
				listTruthButtons[i] = new JButton(buttonText);
				listTruthButtons[i].setPreferredSize(new Dimension(15, 15));
				listTruthButtons[i].setMaximumSize(new Dimension(15, 15));
				listTruthButtons[i].setMinimumSize(new Dimension(15, 15));
				listTruthButtons[i].setActionCommand("btntruth" + i);
				listTruthButtons[i].addActionListener(this);
				listTruthButtons[i].setBorder(BorderFactory.createEmptyBorder());
				listTruthButtons[i].setForeground(Color.BLACK);
	
				if (truthTable[i].equals("1")) {
					listTruthButtons[i].setBackground(new Color(40, 150, 97));
				} else if (truthTable[i].equals("x")) {
					listTruthButtons[i].setBackground(Color.lightGray);
				} else {
					listTruthButtons[i].setBackground(Color.WHITE);
				}
	
				Border line = new LineBorder(Color.BLACK);
				Border margin = new EmptyBorder(5, 5, 5, 5);
				Border compound = new CompoundBorder(line, margin);
				listTruthButtons[i].setBorder(compound);
	
				// listButtons[i].setBorder(null);
				panTruthTable.add(listTruthButtons[i]);
			}
		}
	
		private void updateKMapPanel(String[][] valueTable, String[][] KMap) {
			int rows = 0, columns = 0;
			int i = 0, j = 0;
			// add 1 for the label
			rows = valueTable.length;
			columns = valueTable[0].length;
	
			String[] rowValue = new String[columns];
			String cellValue = "";
			listKMapButtons = new JButton[rows][columns];
			JTextField aTextFields;
	
			panKMap.removeAll();
			panKMap.revalidate();
			panKMap.repaint();
			panKMap.setLayout(new GridLayout(rows + 1, columns + 1, 5, 5));
	
			// add title
			// an empty cell
			if (VARIABLE == 2)
				cellValue = VARIABLE_NAME[0] + " / " + VARIABLE_NAME[1];
			else if (VARIABLE == 3)
				cellValue = VARIABLE_NAME[0] + VARIABLE_NAME[1] + " / " + VARIABLE_NAME[2];
			else if (VARIABLE == 4)
				cellValue = VARIABLE_NAME[0] + VARIABLE_NAME[1] + " / " + VARIABLE_NAME[2] + VARIABLE_NAME[3];
	
			aTextFields = new JTextField(cellValue);
			aTextFields.setPreferredSize(new Dimension(15, 15));
			aTextFields.setMaximumSize(new Dimension(15, 15));
			aTextFields.setMinimumSize(new Dimension(15, 15));
			aTextFields.setEditable(false);
			aTextFields.setBorder(BorderFactory.createEmptyBorder());
			aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
			panKMap.add(aTextFields);
	
			rowValue = valueTable[0];
			for (j = 0; j < rowValue.length; j++) {
				if (VARIABLE == 2)
					cellValue = rowValue[j].substring(0, 1);
				else if (VARIABLE == 3)
					cellValue = rowValue[j].substring(0, 2);
				else if (VARIABLE == 4)
					cellValue = rowValue[j].substring(0, 2);
	
				aTextFields = new JTextField(cellValue);
				aTextFields.setPreferredSize(new Dimension(15, 15));
				aTextFields.setMaximumSize(new Dimension(15, 15));
				aTextFields.setMinimumSize(new Dimension(15, 15));
				aTextFields.setEditable(false);
				aTextFields.setBorder(BorderFactory.createEmptyBorder());
				aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
				panKMap.add(aTextFields);
			}
	
			for (i = 0; i < rows; i++) {
				rowValue = valueTable[i];
				for (j = 0; j < rowValue.length; j++) {
					// add title
					if (j == 0) {
						if (VARIABLE == 2)
							cellValue = rowValue[j].substring(1);
						else if (VARIABLE == 3)
							cellValue = rowValue[j].substring(2);
						else if (VARIABLE == 4)
							cellValue = rowValue[j].substring(2);
	
						aTextFields = new JTextField(cellValue);
						aTextFields.setPreferredSize(new Dimension(15, 15));
						aTextFields.setMaximumSize(new Dimension(15, 15));
						aTextFields.setMinimumSize(new Dimension(15, 15));
						aTextFields.setEditable(false);
						aTextFields.setBorder(BorderFactory.createEmptyBorder());
						aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
						aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
						panKMap.add(aTextFields);
					}
					// button
					String buttonText = "0";
					if (KMap[i][j].equals("1")) {
						buttonText = "1";
					} else if (KMap[i][j].equals("x")) {
						buttonText = "x";
					} else {
						buttonText = "0";
					}
	
					listKMapButtons[i][j] = new JButton(buttonText);
					listKMapButtons[i][j].setPreferredSize(new Dimension(15, 15));
					listKMapButtons[i][j].setMaximumSize(new Dimension(15, 15));
					listKMapButtons[i][j].setMinimumSize(new Dimension(15, 15));
					listKMapButtons[i][j].setActionCommand("btnkamap" + i + "_" + j);
					listKMapButtons[i][j].addActionListener(this);
					listKMapButtons[i][j].setBorder(BorderFactory.createEmptyBorder());
	
					listKMapButtons[i][j].setForeground(Color.BLACK);
	
					if (KMap[i][j].equals("1")) {
						listKMapButtons[i][j].setBackground(new Color(40, 150, 97));
					} else if (KMap[i][j].equals("x")) {
						listKMapButtons[i][j].setBackground(Color.lightGray);
					} else {
						listKMapButtons[i][j].setBackground(Color.WHITE);
					}
					Border line = new LineBorder(Color.BLACK);
					Border margin = new EmptyBorder(5, 5, 5, 5);
					Border compound = new CompoundBorder(line, margin);
					listKMapButtons[i][j].setBorder(compound);
	
					// listButtons[i].setBorder(null);
					panKMap.add(listKMapButtons[i][j]);
				}
			}
		}
	
		private String[] getRowOrColumn (String[] aValueTruthTable, int anIndex,
				String[][] aValueKMapTable, int row, int column, int direction)
		{
			String[] theReturn = new String[2];
			String theValue;
			int i, j;
			
			switch (direction)
			{
			case 1:
				theValue = aValueTruthTable[anIndex];
				for (i = 0; i < aValueKMapTable.length; i++)
				{
					for (j = 0; j < aValueKMapTable[i].length; j++)
					{
						if (theValue.equalsIgnoreCase(aValueKMapTable[i][j]))
						{
							theReturn[0] = ""+ i;
							theReturn[1] = "" + j;
							
							return theReturn;
						}
					}
				}
				break;
				
			case 2:
				theValue = aValueKMapTable[row][column];
				for (i = 0; i < aValueTruthTable.length; i++)
				{
					if (theValue.equalsIgnoreCase(aValueTruthTable[i]))
					{
						theReturn[0] = ""+ i;
						theReturn[1] = "-1";
						
						return theReturn;
					}
				}
				
				break;
			}
			
			return theReturn;
		}
		
		private String getFunctionOutput (String[][] aValueKMapTable, String[][] theKMapTable)
		{
			String functionOutput = "";
			String theCellValue = "";
			String theValue = ""; 
			String oneTerm = "";
			int index = 0;
			
			// loop through the matrix vertically
			for (int j = 0; j < theKMapTable[0].length; j++)
			{
				for (int i = 0; i < theKMapTable.length; i++)
				{
					if (!theKMapTable[i][j].equals("0"))
					{
						theCellValue = aValueKMapTable[i][j];
						index = 0;
						oneTerm = "";
						
						if (!functionOutput.isEmpty())
						{
							functionOutput += "+";
						}
						
						while (theCellValue.length() > 0)
						{
							theValue = theCellValue.substring(0,1);
							theCellValue = theCellValue.substring(1);
																			
							if (theValue.equals("0"))
							{
								oneTerm += VARIABLE_NAME[index] + "'";
							}
							else
							{
								oneTerm += VARIABLE_NAME[index];
							}
							index++;
						}
						functionOutput += oneTerm;
					}
				}
			}
			
			return functionOutput;
		}
	
		private int[] getSubArray (String[] theTruthTable, int subArrayType)
		{
			int[] anArray = new int[theTruthTable.length + 1];
			int i, arrayIndex = 0;
			
			for (i = 0; i< anArray.length; i++)
			{
				anArray[i] = -1;
			}
			// for the 1s
			if (subArrayType == 1)
			{
				for (i = 0, arrayIndex = 0; i< theTruthTable.length; i++)
				{
					if (theTruthTable[i].equals("1"))
					{
						anArray[arrayIndex] = i;
						arrayIndex++;
					}
				}
				//anArray[arrayIndex] = -1;
			}
			// for don't care
			else if (subArrayType == 2)
			{
				for (i = 0, arrayIndex = 0; i< theTruthTable.length; i++)
				{
					if (theTruthTable[i].equals("x"))
					{
						anArray[arrayIndex] = i;
						arrayIndex++;
					}
				}
				//anArray[arrayIndex] = -1;
			}
			
			return anArray;
		}
	
	}

	class Simplifier {
		private int ARRAY_MAGNITUDE = 20;
		private String[] MINTERMS = new String[ARRAY_MAGNITUDE];
		private String[] IGNORANCE = new String[ARRAY_MAGNITUDE];
		private String[] PASS_IGNORANCE = new String[ARRAY_MAGNITUDE];
		private String[][] PRIME_CALC = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
	
		private String[] SUB_MINTERMS = new String[ARRAY_MAGNITUDE];
		private int SUB_MIN_INDEX = 0;
		private char IS_COMPLETE = 'F';
		private char IS_FIRST_PRIME = 'T';
		private int NUMBER_OF_VARIABLE = 0;
		
		private String[] VARIABLE_NAME = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N"};
		
	
		public String getSimplifier(int[] arrMinTerms, int[] arrIgnorances, int variable) {
			String functionSimplify = "";
			NUMBER_OF_VARIABLE = variable;
			
			
			MINTERMS = toBinary(arrMinTerms);
			IGNORANCE = toBinary(arrIgnorances);
	
			functionSimplify = completeMethods();
			return functionSimplify;
		}
	
		
		private void printArray (String[] anArray)
		{
			for (int i = 0; i < anArray.length; i++)
			{
				System.out.print(anArray[i] + " ");
			}
			System.out.println();
		}
	
		private String[] toBinary(int[] minArray) {
			int a = 0;
			String[] returnMinTerms = new String[200];
			returnMinTerms = fill1DArray(returnMinTerms);
	
			while (minArray[a] != -1) {
				returnMinTerms[a] = toBinaryString(minArray[a]);
				a++;
			}
			return returnMinTerms;
		}
	
		private String completeMethods() {
			int a = 0, b = 0;
			String[][] finalPass = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
	
			String[][] remainingMinterms = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
			remainingMinterms = fillArrays(remainingMinterms);
	
			finalPass = fillArrays(finalPass);
			PRIME_CALC = fillArrays(PRIME_CALC);
			PASS_IGNORANCE = fill1DArray(PASS_IGNORANCE);
			SUB_MINTERMS = fill1DArray(SUB_MINTERMS);
	
			finalPass = fillMinterms(MINTERMS);		
			while (MINTERMS[a] != "-1") {
				a++;
			}
	
			while (IGNORANCE[b] != "-1") {
				MINTERMS[a] = IGNORANCE[b];
				a++;
				b++;
			}
	
			a = 0;
			b = 0;
	
			finalPass = fillMinterms(MINTERMS);
	
			for (int i = 0; i < finalPass.length; i++) {
				for (int j = 0; j < finalPass[i].length; j++) {
					if (finalPass[i][j] != "-1") {
						PRIME_CALC[b][0] = finalPass[i][j];
						b++;
					}
				}
	
			}
			PASS_IGNORANCE = fillDontCare(IGNORANCE);
			while (IS_COMPLETE != 'T') {
				finalPass = compareAdjacentMinterms(finalPass);
			}
	
			removeDuplicatePrimes();
			remainingMinterms = getEssentialPrimes();
			if (remainingMinterms[0][0] != "-1") {
				remainingMinterms = getPrimes(remainingMinterms);
			}
	
			a = 0;
			b = 0;
	
			while (PRIME_CALC[a][0] != "-1" && PRIME_CALC[a][0] != "D" && PRIME_CALC[a][0] != "Y") {
				SUB_MINTERMS[SUB_MIN_INDEX] = PRIME_CALC[a][0];
				SUB_MIN_INDEX++;
				a++;
			}
	
			return giveOutput(SUB_MINTERMS);
		}
	
		private String[][] fillMinterms(String[] arrMinterms) {
			String tempMinterm;
			int a = 0, count;
			int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0, sevens = 0, eights = 0, nines = 0,
					tens = 0, elevens = 0, twelves = 0, thirteens = 0;
			String[][] groupWise = new String[100][100];
			fillArrays(groupWise);
	
			while (arrMinterms[a] != "-1") {
				count = 0;
				tempMinterm = arrMinterms[a];
	
				while (tempMinterm.length() < NUMBER_OF_VARIABLE) {
					tempMinterm = "0" + tempMinterm;
				}
	
				for (int i = 0; i < tempMinterm.length(); i++) {
					if (tempMinterm.charAt(i) == '1') {
						count++;
					}
				}
	
				if (count == 0) {
					groupWise[0][0] = tempMinterm;
				} else if (count == 1) {
					groupWise[1][ones] = tempMinterm;
					ones++;
				} else if (count == 2) {
					groupWise[2][twos] = tempMinterm;
					twos++;
				} else if (count == 3) {
					groupWise[3][threes] = tempMinterm;
					threes++;
				} else if (count == 4) {
					groupWise[4][fours] = tempMinterm;
					fours++;
				} else if (count == 5) {
					groupWise[5][fives] = tempMinterm;
					fives++;
				} else if (count == 6) {
					groupWise[6][sixes] = tempMinterm;
					sixes++;
				} else if (count == 7) {
					groupWise[7][sevens] = tempMinterm;
					sevens++;
				} else if (count == 8) {
					groupWise[8][eights] = tempMinterm;
					eights++;
				} else if (count == 9) {
					groupWise[9][nines] = tempMinterm;
					nines++;
				} else if (count == 10) {
					groupWise[10][tens] = tempMinterm;
					tens++;
				} else if (count == 11) {
					groupWise[11][elevens] = tempMinterm;
					elevens++;
				} else if (count == 12) {
					groupWise[12][twelves] = tempMinterm;
					twelves++;
				} else if (count == 13) {
					groupWise[12][thirteens] = tempMinterm;
					thirteens++;
				}
	
				a++;
			}
	
			return groupWise;
		}
	
		private String[] fillDontCare(String[] arrMinterms) {
			String[] groupWiseIgnorance = new String[ARRAY_MAGNITUDE];
			groupWiseIgnorance = fill1DArray(groupWiseIgnorance);
			String tempMinterm;
			int a = 0, b = 0;
	
			while (arrMinterms[a] != "-1") {
	
				tempMinterm = arrMinterms[a];
	
				while (tempMinterm.length() < NUMBER_OF_VARIABLE) {
					tempMinterm = "0" + tempMinterm;
				}
	
				groupWiseIgnorance[b] = tempMinterm;
				b++;
				a++;
			}
	
			return groupWiseIgnorance;
		}
	
		private String[][] compareAdjacentMinterms(String[][] groupWise) {
			String[][] allArrayCombined = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
			int count = 0, c = 0;
			int index = 0;
			int minComp = 0, minComp2 = 1, minComp1 = 0, minComp3 = 0;
			int index1 = 0, index2 = 0;
	
			for (int i = 0; i < ARRAY_MAGNITUDE; i++) {
				for (int j = 0; j < ARRAY_MAGNITUDE; j++) {
					allArrayCombined[i][j] = "-1";
				}
			}
			while (c < groupWise.length) {
				if (groupWise[c][0] != "-1" && groupWise[c + 1][0] != "-1")
					count++;
				c++;
			}
	
			if (count >= 1) {
				count = 0;
				while (minComp < groupWise.length - 1) {
	
					while (groupWise[minComp][minComp1] != "-1") {
	
						while (groupWise[minComp2][minComp3] != "-1") {
	
							for (int ij = 0; ij < NUMBER_OF_VARIABLE; ij++) {
	
								if (groupWise[minComp][minComp1].charAt(ij) != groupWise[minComp2][minComp3]
										.charAt(ij)) {
									count++;
									index = ij;
								}
							}
	
							if (count == 1) {
	
								allArrayCombined[index1][index2] = groupWise[minComp2][minComp3].substring(0, index) + 'x'
										+ groupWise[minComp2][minComp3].substring(index + 1, NUMBER_OF_VARIABLE);
	
								primeFill(allArrayCombined[index1][index2], groupWise[minComp][minComp1],
										groupWise[minComp2][minComp3]);
								index2++;
	
							}
	
							count = 0;
							minComp3++;
	
						}
	
						minComp3 = 0;
	
						minComp1++;
	
					}
	
					minComp++;
					minComp2++;
					minComp1 = 0;
	
					index1++;
	
					index2 = 0;
					minComp3 = 0;
	
				}
				IS_FIRST_PRIME = 'F';
			}
	
			else
				IS_COMPLETE = 'T';
			return allArrayCombined;
		}
	
		private void primeFill(String result, String op1, String op2) {
			int j = 1;
			if (IS_FIRST_PRIME == 'T') {
				for (int i = 0; i < PRIME_CALC.length; i++) {
	
					if (PRIME_CALC[i][0] == op1 || PRIME_CALC[i][0] == op2) {
	
						while (PRIME_CALC[i][j] != "-1") {
							j++;
						}
						PRIME_CALC[i][j] = result;
						j = 1;
					}
	
				}
			} else {
	
				for (int m = 0; m < PRIME_CALC.length; m++) {
					for (int n = 0; n < PRIME_CALC[m].length; n++) {
						if (PRIME_CALC[m][n] == op1 || PRIME_CALC[m][n] == op2) {
	
							PRIME_CALC[m][n] = result;
	
						}
					}
	
				}
	
			}
		}
	
		private void removeDuplicatePrimes() {
			int primeCalcIndex = 0, currentIndex = 1, checkIndex = 2, tempCheckIndex = 0;
	
			while (PRIME_CALC[primeCalcIndex][0] != "-1") {
				while (PRIME_CALC[primeCalcIndex][currentIndex] != "-1") {
					checkIndex = currentIndex + 1;
					while (PRIME_CALC[primeCalcIndex][checkIndex] != "-1") {
						tempCheckIndex = checkIndex;
	
						if (PRIME_CALC[primeCalcIndex][currentIndex].equals(PRIME_CALC[primeCalcIndex][checkIndex])) {
	
							while (PRIME_CALC[primeCalcIndex][tempCheckIndex] != "-1") {
								PRIME_CALC[primeCalcIndex][tempCheckIndex] = PRIME_CALC[primeCalcIndex][tempCheckIndex
										+ 1];
								tempCheckIndex++;
							}
	
							PRIME_CALC[primeCalcIndex][tempCheckIndex] = "-1";
							checkIndex--;
						}
	
						checkIndex++;
					}
					currentIndex++;
				}
	
				currentIndex = 1;
				checkIndex = 2;
				primeCalcIndex++;
			}
	
		}
	
		private String[][] getEssentialPrimes() {
			int i = 0, j = 0;
			int a = 0, b = 0;
			SUB_MIN_INDEX = 0;
			String[] tempBack = new String[ARRAY_MAGNITUDE];
			tempBack = fill1DArray(tempBack);
	
			String[][] tempPrimeCalc = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
			tempPrimeCalc = fillArrays(tempPrimeCalc);
	
			while (PRIME_CALC[i][0] != "-1") {
				while (PASS_IGNORANCE[j] != "-1") {
					if (PRIME_CALC[i][0].equals(PASS_IGNORANCE[j])) {
						PRIME_CALC[i][0] = "D";
					}
					j++;
				}
				j = 0;
				i++;
			}
	
			i = 0;
			j = 0;
			while (PRIME_CALC[i][0] != "-1") {
				if (PRIME_CALC[i][2].equals("-1") && PRIME_CALC[i][0] != "D" && PRIME_CALC[i][1] != "-1") {
					SUB_MINTERMS[SUB_MIN_INDEX] = PRIME_CALC[i][1];
					SUB_MIN_INDEX++;
	
					while (PRIME_CALC[a][0] != "-1") {
						while (PRIME_CALC[a][b] != "-1") {
							if (PRIME_CALC[a][b].equals(PRIME_CALC[i][1])) {
								PRIME_CALC[a][0] = "Y";
							}
	
							b++;
						}
						b = 0;
						a++;
					}
					a = 0;
					b = 0;
	
				}
				i++;
			}
	
			a = 0;
			b = 0;
			int c = 0, d = 0;
			while (PRIME_CALC[a][0] != "-1") {
				if (PRIME_CALC[a][0] != "D" && PRIME_CALC[a][0] != "Y") {
					while (PRIME_CALC[a][b] != "-1") {
						tempPrimeCalc[c][d] = PRIME_CALC[a][b];
						b++;
						d++;
	
					}
					c++;
				}
				a++;
	
				b = 0;
				d = 0;
			}
	
			return tempPrimeCalc;
	
		}
	
		private String[][] getPrimes(String[][] tempPrimeCalc) {
			int a = 0, b = 1, c = 0, d = 0, i = 0;
			int count = 0;
			String maxMinterm;
			int maxIndex = 0;
			String[] primes = new String[ARRAY_MAGNITUDE];
			primes = fill1DArray(primes);
	
			int[] primeCount = new int[ARRAY_MAGNITUDE];
			primeCount = fill1DIntArray(primeCount);
	
			String[][] tempPrimeCalc2 = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
			tempPrimeCalc2 = fillArrays(tempPrimeCalc2);
	
			while (tempPrimeCalc[a][0] != "-1") {
				while (tempPrimeCalc[a][b] != "-1") {
					primes[i] = tempPrimeCalc[a][b];
					b++;
					i++;
				}
				a++;
			}
	
			a = 0;
			i = 0;
			while (primes[i] != "-1") {
				while (tempPrimeCalc[a][0] != "-1") {
					while (tempPrimeCalc[a][b] != "-1") {
						if (primes[i] == tempPrimeCalc[a][b]) {
							count++;
							b = 1;
							break;
						}
	
						b++;
					}
					b = 1;
					a++;
				}
				a = 0;
				b = 1;
				i++;
				primeCount[c] = count;
				count = 0;
				c++;
			}
	
			count = 0;
			c = 0;
			while (primeCount[c] != -1) {
				if (primeCount[c] > count) {
					count = primeCount[c];
					maxIndex = c;
				}
				c++;
			}
			a = 0;
			b = 1;
			maxMinterm = primes[maxIndex];
			if (maxMinterm != "-1") {
				SUB_MINTERMS[SUB_MIN_INDEX] = maxMinterm;
				SUB_MIN_INDEX++;
			}
	
			while (tempPrimeCalc[a][0] != "-1") {
				while (tempPrimeCalc[a][b] != "-1") {
					if (tempPrimeCalc[a][b].equals(maxMinterm)) {
						tempPrimeCalc[a][0] = "Y";
					}
	
					b++;
				}
				b = 0;
				a++;
			}
	
			a = 0;
			b = 0;
			c = 0;
			d = 0;
	
			while (PRIME_CALC[a][0] != "-1") {
				while (PRIME_CALC[a][b] != "-1") {
					if (PRIME_CALC[a][b].equals(maxMinterm)) {
						PRIME_CALC[a][0] = "Y";
					}
	
					b++;
				}
				b = 0;
				a++;
			}
	
			a = 0;
			b = 0;
			c = 0;
			d = 0;
			while (tempPrimeCalc[a][0] != "-1") {
				if (tempPrimeCalc[a][0] != "Y") {
					while (tempPrimeCalc[a][b] != "-1") {
						tempPrimeCalc2[c][d] = tempPrimeCalc[a][b];
						b++;
						d++;
	
					}
					c++;
				}
				a++;
	
				b = 0;
				d = 0;
			}
	
			return tempPrimeCalc2;
	
		}
	
		private String giveOutput(String[] arrOutput) {
			int count = 0;
			String[] minimizedArray = new String[ARRAY_MAGNITUDE];
			fill1DArray(minimizedArray);
			int minimizedIndex = 0;
			String outputString = "";
			String tempMinterm = "";
			String tempMintermCheck = "";
			int i = 0;
	
			while (arrOutput[i] != "-1") {
				for (int ij = NUMBER_OF_VARIABLE - 1; ij >= 0; ij--) {
					if (arrOutput[i].charAt(ij) != 'x') {
						if (arrOutput[i].charAt(ij) == '0') {						
							tempMintermCheck = VARIABLE_NAME[ij] + "'";
						}
						if (arrOutput[i].charAt(ij) == '1') {						
							tempMintermCheck = VARIABLE_NAME[ij];
						}
						tempMinterm = tempMintermCheck + tempMinterm;
	
					}
				}
	
				i++;
	
				for (int j = 0; j < minimizedArray.length; j++) {
	
					if (minimizedArray[j].equals(tempMinterm)) {
						count++;
					}
				}
	
				if (count == 0) {
					minimizedArray[minimizedIndex] = tempMinterm;
					minimizedIndex++;
				}
				tempMinterm = "";
				count = 0;
	
			}
			i = 0;
	
			minimizedIndex = 0;
			while (minimizedArray[minimizedIndex] != "-1") {
				outputString = outputString + "+" + minimizedArray[minimizedIndex];
				minimizedIndex++;
			}
			outputString = outputString.substring(1, outputString.length());
			return outputString;
		}
	
		/*
		private String[][] fillDontCares(String[][] arrToFill) {
			int final_pass_dontcare_index = 0;
			while (PASS_IGNORANCE[final_pass_dontcare_index] != "-1") {
				for (int i = 0; i < arrToFill.length; i++) {
					for (int j = 0; j < arrToFill[i].length; j++) {
						if (PASS_IGNORANCE[final_pass_dontcare_index].equals(arrToFill[i][j])) {
							arrToFill[i][j] = "-1";
	
						}
					}
				}
				final_pass_dontcare_index++;
			}
			return arrToFill;
		}
		*/
	
		private String[][] fillArrays(String[][] allArrayCombined) {
			for (int i = 0; i < allArrayCombined.length; i++) {
				for (int j = 0; j < allArrayCombined[i].length; j++) {
					allArrayCombined[i][j] = "-1";
				}
			}
	
			return allArrayCombined;
		}
	
		/*
		private int[][] fillIntArrays(int[][] allArrayCombined) {
			for (int i = 0; i < allArrayCombined.length; i++) {
				for (int j = 0; j < allArrayCombined[i].length; j++) {
					allArrayCombined[i][j] = -1;
				}
			}
	
			return allArrayCombined;
		}
		*/
	
		private String[] fill1DArray(String[] arrToFill) {
			for (int i = 0; i < arrToFill.length; i++) {
				arrToFill[i] = "-1";
			}
			return arrToFill;
		}
	
		private int[] fill1DIntArray(int[] arrToFill) {
	
			for (int i = 0; i < arrToFill.length; i++) {
				arrToFill[i] = -1;
			}
	
			return arrToFill;
		}
	
		private String toBinaryString(int aValue) {
			int quotient = aValue, remainder;
			String result = "";
			
			if (aValue == 0)
			{
				while (result.length() < NUMBER_OF_VARIABLE)
				{
					result += "0";
				}
				return result;
			}
			
			if (aValue == 1)
			{
				result = "1";
				while (result.length() < NUMBER_OF_VARIABLE)
				{
					result = "0" + result;
				}
				return result;
			}
				
			while (quotient != 0) {
	
				remainder = quotient % 2;
				quotient = quotient / 2;
				result = Integer.toString(remainder) + result;
			}
			
			return result;
		}
	
	}
