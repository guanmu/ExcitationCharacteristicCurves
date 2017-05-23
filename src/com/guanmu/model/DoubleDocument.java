package com.guanmu.model;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DoubleDocument extends PlainDocument {

	private static final long serialVersionUID = 1893457792930897005L;
	
	private static final String MARK_REGEX = "^[-+]?";
	
	private static final String POINT_REGEX = "^[-+]?\\d+\\.$";
	
	private static final String DOUBLE_REGEX = "^[-+]?\\d+(\\.\\d+)?$";
	String currentValue = "";

    public String getValue() {
      return currentValue;
    }
    
    @Override
    public void insertString(int offset, String string,
        AttributeSet attributes) throws BadLocationException {

      if (string == null) {
        return;
      } else {
        String newValue;
        int length = getLength();
        if (length == 0) {
          newValue = string;
        } else {
          String currentContent = getText(0, length);
          StringBuffer currentBuffer = 
               new StringBuffer(currentContent);
          currentBuffer.insert(offset, string);
          newValue = currentBuffer.toString();
        }
        currentValue = checkInput(newValue, offset);
        super.insertString(offset, string, attributes);
      }
    }
    
    @Override
    public void remove(int offset, int length)
        throws BadLocationException {
      int currentLength = getLength();
      String currentContent = getText(0, currentLength);
      String before = currentContent.substring(0, offset);
      String after = currentContent.substring(length+offset,
        currentLength);
      String newValue = before + after;
      currentValue = checkInput(newValue, offset);
      super.remove(offset, length);
    }
    
    public String checkInput(String proposedValue, int offset)
        throws BadLocationException {
      if (proposedValue.length() > 0) {
			
    	  	if (proposedValue.matches(MARK_REGEX)) {
    	  		return proposedValue;
    	  	}
			
			if (proposedValue.matches(POINT_REGEX)) {
				return proposedValue;
			}
			
			if (proposedValue.matches(DOUBLE_REGEX)) {
				return proposedValue;
			}
			
			throw new BadLocationException(proposedValue, offset);
      } else {
        return "";
      }
    }

}
