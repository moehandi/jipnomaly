/*
 * @author : moehandi
 * @Teknik Informatika UNIB
 */

package sistem.validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author mohandi
 */
public class PortCharValidator extends PlainDocument{

    private int maxCharacter;
    private boolean numOnly;
    private String numChars = "0123456789";

    public PortCharValidator(){

        this(-1, false);
    }

    public PortCharValidator(int maxCharacter, boolean numOnly) {
        this.maxCharacter = maxCharacter;
        this.numOnly = numOnly;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (numOnly) {
            if (maxCharacter == -1) {
                if (checkString(str)) {
                    super.insertString(offs, str, a);
                }
            } else {
                int panjangTextLama = getLength();
                int panjangTextBaru = str.length();
                if ((panjangTextLama + panjangTextBaru) <= maxCharacter) {
                    if (checkString(str)) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        } else {
            if (maxCharacter == -1) {
                super.insertString(offs, str, a);
            } else {
                int panjangTextLama = getLength();
                int panjangTextBaru = str.length();
                if ((panjangTextLama + panjangTextBaru) <= maxCharacter) {
                    super.insertString(offs, str, a);
                }
            }
        }
    }

    private boolean checkString(String input) {
        boolean result = false;
        for (int i = 0; i < input.length(); i++) {
            if (numChars.indexOf(input.charAt(i)) == -1) {
                result = false;
                break;
            } else {
                result = true;
            }
        }
        return result;
    }
}


