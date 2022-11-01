import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * The CurrencyCalcuator program creates a simple GUI that allows
 * user input to convert a number of currencies to other currencies
 *
 * This class parses JSON from a live currency API and then does
 * a currency conversion calculation
 *
 * @author  Kyle Garrett
 * @version .3
 * @since   2019-03-15
 */

public class CurrencyExchange
{
    // Instance Variables
    private String exchangeFrom, exchangeTo, currencySymbol;

    /**
     * This constructor takes a from and a to currency String that
     * is set to their respective instance variables.
     *
     * @param exchFrom A currency that is being exchanged from
     * @param exchTo A currency that is being exchanged to
     */
    public CurrencyExchange(String exchFrom, String exchTo)
    {
        this.exchangeFrom = exchFrom;
        this.exchangeTo = exchTo;
    }

    /**
     * This method parses the JSON from the API and then sets the
     * desired currency exchange rate to an instance variable.
     * The parameter is the amount entered by the user to be
     * converts. This parameter is calculated against the
     * exchange rate to provide a value rounded to two decimal
     * places. A stringbuilder is included that adds the
     * respective currency symbol to the front of the value.
     *
     * @param amountEnt A double entered by the user, this is
     *                  what is used to calculate against the
     *                  exchange rate.
     * @return String with a symbol and the value of the conversion
     * @throws IOException Thrown whenever no amount, no exchange rate,
     * or some other unknown error
     */
    public String getRate(double amountEnt) throws IOException {
        // String for URL being used for currency conversion
        String sURL = "https://api.exchangeratesapi.io/latest?base="+exchangeFrom;

        // String builder for adding currency sign to the start of the output
        StringBuilder sb = new StringBuilder();

        // Uses the UrlConnection class to connect to the API
        URL url = new URL(sURL);
        URLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Parses the JSON
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new InputStreamReader((InputStream) request.getContent()));

        // First makes an jsonObject from the original JSON, then the second line makes a jsonObject from everything under rates.
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject jsonRates = jsonObject.getAsJsonObject("rates");


        // Writes parsed info to a variable & then calculates it to a currency exchange formula.
        String jsonParsedRate = jsonRates.get(exchangeTo).toString();
        double exchangeRate = Double.parseDouble(jsonParsedRate);
        double productOfRates = (exchangeRate)*(amountEnt);
        double roundedProduct = Math.round(productOfRates * 100.0) / 100.0;

        // If statement that assigns the correct currency symbol
        if (exchangeTo.matches("USD|CAD|AUD"))
        {
            currencySymbol = "$";
        }
        else if (exchangeTo.equals("EUR"))
        {
            currencySymbol = "€";
        }
        else if (exchangeTo.equals("JPY"))
        {
            currencySymbol = "¥";
        }
        else if (exchangeTo.equals("GBP"))
        {
            currencySymbol = "£";
        }
        else if (exchangeTo.equals("CHF"))
        {
            currencySymbol = "CHF ";
        }
        else if (exchangeTo.equals("ZAR"))
        {
            currencySymbol = "R ";
        }

        // Stringbuilder inserts the two strings together. Currency symbol is in front.
        sb.insert(0, roundedProduct);
        sb.insert(0, currencySymbol);

        return (sb.toString());
    }
}
