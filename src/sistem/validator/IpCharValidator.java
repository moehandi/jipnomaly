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
public class IpCharValidator extends PlainDocument{

    private int maxCharacter=18;
    private boolean kdOnly;
    private String kdChars = "./0123456789";

    public IpCharValidator(){

        this(-1, false);
    }

    public IpCharValidator(int maxCharacter, boolean kdOnly) {
        this.maxCharacter = maxCharacter;
        this.kdOnly = kdOnly;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (kdOnly) {
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
            if (kdChars.indexOf(input.charAt(i)) == -1) {
                result = false;
                break;
            } else {
                result = true;
            }
        }
        return result;
    }
}


