package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            Translator translator = new JSONTranslator();

            String [] items = new String[translator.getCountryCodes().size()];
            String displayLanguage = "en";
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                String countryName = translator.translate(countryCode, displayLanguage);
                items[i++] = countryName;
            }
            JList<String> list = new JList<>(items);
            JScrollPane scrollPane = new JScrollPane(list);

            countryPanel.add(scrollPane);

            JPanel languagePanel = new JPanel();

            JSONTranslator jsonTranslator = new JSONTranslator();
            LanguageCodeConverter langconverter = new LanguageCodeConverter();
            List<String> languages = jsonTranslator.getLanguageCodes();
            String[] languagesArray = new String[languages.size()];
            for(int i = 0; i < languages.size(); i++) {
                languagesArray[i] = langconverter.fromLanguageCode(languages.get(i));
            }
//            JTextField languageField = new JTextField(10);
            JComboBox<String> languageCombo = new JComboBox<>(languagesArray);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageCombo);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


//                    String language = languageField.getText();
                    String language = langconverter.fromLanguage((String) languageCombo.getSelectedItem());
                    String country = list.getSelectedValue();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
