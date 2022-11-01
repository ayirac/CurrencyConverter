import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

/**
 * The CurrencyCalcuator program creates a simple GUI that allows
 * user input to convert a number of currencies to other currencies
 *
 * This class contains the methods used by the CurrencyGUI.form
 *
 * @author  Kyle Garrett
 * @version .3
 * @since   2019-03-15
 */
public class CurrencyGUI extends JFrame
{
    private JPanel rootPanel;
    private JRadioButton fromUsdRButton;
    private JRadioButton fromEurRButton;
    private JRadioButton fromJpyRButton;
    private JRadioButton fromGbpRButton;
    private JRadioButton fromChfRButton;
    private JRadioButton fromCadRButton;
    private JRadioButton fromAudRButton;
    private JRadioButton fromZarRButton;
    private JRadioButton toUsdRButton;
    private JRadioButton toEurRButton;
    private JRadioButton toJpyRButton;
    private JRadioButton toGbpRButton;
    private JRadioButton toChfRButton;
    private JRadioButton toCadRButton;
    private JRadioButton toAudRButton;
    private JRadioButton toZarRButton;
    private JButton buttonCalculate;
    private JTextField jtextfieldFrom;
    private JTextField jtextfieldTo;
    private JPanel titlePanel;
    private JLabel titleJLabel;

    private String selectedFrom, selectedTo, amountEntered;
    private double amountEnteredParsed;


    public CurrencyGUI()
    {
        ImageIcon buttonClickedIcon = new ImageIcon("src/resources/button1AfterClick.png");

        add(rootPanel);
        setTitle("Currency Calculator");
        setSize(425,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        titlePanel.setOpaque(false);
        titleJLabel.setOpaque(false);

        // Makes the button's border invisible and changes the button's imageicon when it's being pressed
        buttonCalculate.setOpaque(false);
        buttonCalculate.setContentAreaFilled(false);
        buttonCalculate.setBorderPainted(false);
        buttonCalculate.setPressedIcon(buttonClickedIcon);
        buttonCalculate.setFocusPainted(false);

        /**
         * Action listener that checks for what 'from' radio button has been selected
         */
        ActionListener fromRadioButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JRadioButton button = (JRadioButton) e.getSource();
                System.out.println("Debug:You select button: " + button.getText());
                selectedFrom = button.getText();
            }
        };

        /**
         * Action listener that checks for what 'to' radio button has been selected
         */
        ActionListener toRadioButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JRadioButton button = (JRadioButton) e.getSource();
                System.out.println("Debug:You select button: " + button.getText());
                selectedTo = button.getText();
            }
        };

        /**
         * Action listener that activates when buttonCalculate has been pressed.
         * It'll use the calculation method from the CurrencyExchange class and provide
         * the currency conversion and various errors if any occur.
         */
        buttonCalculate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CurrencyExchange currencyExchange = new CurrencyExchange(selectedFrom, selectedTo);
                try
                {
                    amountEntered = jtextfieldFrom.getText();
                    amountEnteredParsed = Double.parseDouble(amountEntered);

                    String currencyRate = currencyExchange.getRate(amountEnteredParsed);
                    System.out.println("Debug: Exchanged from " + selectedFrom + " to " + selectedTo + ": " + currencyRate);
                    jtextfieldTo.setText(currencyRate);
                }
                catch (ConnectException e1) // Catches exceptions relating to not being able to connect to the API
                {
                    e1.printStackTrace();
                    System.out.println("Debug: No data received from webserver, is it down?");
                    JOptionPane.showMessageDialog(null, "No data received from webserver, try again later.");
                }
                catch (NumberFormatException e1) // Catches exceptions relating to an interger not being in the textfield
                {
                    e1.printStackTrace();
                    System.out.println("Debug: No number amount entered in the textfield!");
                    JOptionPane.showMessageDialog(null, "No interger amount entered in the textfield.");
                }
                catch (NullPointerException e1) // Catches exception to no currency buttons being selected
                {
                    e1.printStackTrace();
                    System.out.println("Debug: No currency buttons have been selected.");
                    JOptionPane.showMessageDialog(null, "No currency buttons have been selected.");
                }
                catch (IOException e1) // Catches the rest of the exceptions, mostly a currency button not being selected.
                {
                    e1.printStackTrace();
                    System.out.println("Debug: No radiobutton selected.");
                    JOptionPane.showMessageDialog(null, "No currency buttons have been selected." +
                            "\n or another error has occurred.");
                }


            }
        });

        // Adds actionlisteners to each radio button
        fromUsdRButton.addActionListener(fromRadioButtonListener);
        fromEurRButton.addActionListener(fromRadioButtonListener);
        fromJpyRButton.addActionListener(fromRadioButtonListener);
        fromGbpRButton.addActionListener(fromRadioButtonListener);
        fromChfRButton.addActionListener(fromRadioButtonListener);
        fromCadRButton.addActionListener(fromRadioButtonListener);
        fromAudRButton.addActionListener(fromRadioButtonListener);
        fromZarRButton.addActionListener(fromRadioButtonListener);

        toUsdRButton.addActionListener(toRadioButtonListener);
        toEurRButton.addActionListener(toRadioButtonListener);
        toJpyRButton.addActionListener(toRadioButtonListener);
        toGbpRButton.addActionListener(toRadioButtonListener);
        toChfRButton.addActionListener(toRadioButtonListener);
        toCadRButton.addActionListener(toRadioButtonListener);
        toAudRButton.addActionListener(toRadioButtonListener);
        toZarRButton.addActionListener(toRadioButtonListener);
    }

}
