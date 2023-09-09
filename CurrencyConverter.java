import org.json.JSONObject;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Scanner;
import java.net.URL;

import static java.lang.String.format;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {
        HashMap<Integer, String> CurrencyCodes = new HashMap<Integer, String>();
        CurrencyCodes.put(1, "USD");
        CurrencyCodes.put(2, "CAD");
        CurrencyCodes.put(3, "EUR");
        CurrencyCodes.put(4, "HKD");
        CurrencyCodes.put(5, "INR");


        Integer from, to;
        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the currency converter!");
        System.out.println("Currency converter From?");
        System.out.println("1:USD(US Dollar)\t 2:CAD(Canadian Dollar)\t 3:EUR(Euro)\t 4:HKD(Hong Kong Dollar)\t 5:INR(Indian Rupee)");
        from = sc.nextInt();
        while(from <1 || from>5){
            System.out.println("Please select a valid currency from 1-5 ");
            System.out.println("1:USD(US Dollar)\t 2:CAD(Canadian Dollar)\t 3:EUR(Euro)\t 4:HKD(Hong Kong Dollar)\t 5:INR(Indian Rupee)");
            from = sc.nextInt();
        }
        fromCode = CurrencyCodes.get(from);

        System.out.println("Currency converting To?");
        System.out.println("1:USD(US Dollar)\t 2:CAD(Canadian Dollar)\t 3:EUR(Euro)\t 4:HKD(Hong Kong Dollar)\t 5:INR(Indian Rupee)");


        to = sc.nextInt();
        while(to <1 || to>5){
            System.out.println("Please select a valid currency from 1-5 ");
            System.out.println("1:USD(US Dollar)\t 2:CAD(Canadian Dollar)\t 3:EUR(Euro)\t 4:HKD(Hong Kong Dollar)\t 5:INR(Indian Rupee)");
            to = sc.nextInt();
        }
        toCode = CurrencyCodes.get(to);

        System.out.println("Amount you wish to Convert?");
        amount = sc.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);
        System.out.println("Thank you for using the currency converter!");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        String GET_URL = "https://openexchangerates.org/api/latest?base=" +toCode+"&symbols="+fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine=in.readLine()) != null){
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate);
            System.out.println();
            System.out.println(amount + fromCode + " = " +amount/exchangeRate+toCode);

        }else{
            System.out.println("GET request failed");
        }
    }
}
