import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {
Boolean running = true;
        do{
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();
        //Add currency codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");

        Integer from, to;
        String fromCode, toCode;
        double amount;

    Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the currency converter!");
        System.out.println("Currency converting FROM?");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar \t 5:INR (Indin Rupee)");

    from= sc.nextInt();
    while(from < 1 || from >= 5) {
    System.out.println("Please select a valid currency (1-5)");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar \t 5:INR (Indin Rupee)");
        from= sc.nextInt();
}
    fromCode = currencyCodes.get(from);

        System.out.println("Currency converting TO?");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar \t 5:INR (Indin Rupee)");

        to= sc.nextInt();
        while(to < 1 || to >= 5) {
        System.out.println("Please select a valid currency (1-5)");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar \t 5:INR (Indin Rupee)");
        to= sc.nextInt();
    }
    toCode = currencyCodes.get(to);


        System.out.println("Amount you wish to convert?");
        amount = sc.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);
            System.out.println("Would you like to make another conversion?");
            System.out.println("1:Yes \t Any other integer: No");
            if(sc.nextInt() !=1) {
                running = false;
            }

    } while(running);
        System.out.println("Thank you for using the currency converter!");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        DecimalFormat f = new DecimalFormat("00.00");

        String apiKey = "a8997c13e0dc507c8972211f8e6fa6b1";
        String GET_URL = "http://api.exchangeratesapi.io/latest?base=" + toCode + "&symbols=" + fromCode + "&access_key=" + apiKey;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) !=null) {
                response.append(inputLine);
            }in.close();
            JSONObject obj = new JSONObject(response.toString());

            Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate); //keep for debugging
            System.out.println();
            System.out.println(f.format(amount) + fromCode + "=" + f.format(amount/exchangeRate) + toCode);

            }
        else {
            System.out.println("GET request failed");
        }

    }
}