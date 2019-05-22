package kbs2;
import javax.swing.UIManager;

/**
 *
 * @author pieter, randy
 */
public class main {

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
        LoginScreen LS = new LoginScreen();
        ConnectBizagi.updateStockItemHoldings();
        LS.setVisible(true);
    }
}
