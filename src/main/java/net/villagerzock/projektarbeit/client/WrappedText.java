package net.villagerzock.projektarbeit.client;

import io.netty.handler.ssl.DelegatingSslContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

import java.util.ArrayList;
import java.util.List;

public class WrappedText extends MutableText {
    @SuppressWarnings("'MutableText(net.minecraft.text.TextContent, java.util.List<net.minecraft.text.Text>, net.minecraft.text.Style)' is not public in 'net.minecraft.text.MutableText'. Cannot be accessed from outside package")
    public WrappedText(TextContent content, List<Text> siblings, Style style){
        super(content,siblings,style);
    }
    public static WrappedText of(Text text){
        WrappedText wrappedText = new WrappedText(text.getContent(),text.getSiblings(),text.getStyle());
        return wrappedText;
    }
    public int length(){
        return 0;
    }
    public Text subtext(int index,boolean isIndexBegin){
        if (isIndexBegin){
            return subtext(index,length());
        }else {
            return subtext(0,index);
        }
    }
    public Text subtext(int beginIndex, int endIndex){
        MutableText result = Text.empty();
        List<Text> texts = new ArrayList<>();
        texts.add(this);
        texts.addAll(getSiblings());
        int currentIndex = 0;
        for (Text sibling : texts){
            if (currentIndex + sibling.getString().length() >= beginIndex && currentIndex < beginIndex){
                for (Text text : clipBeginning(beginIndex,sibling)){
                    result.append(text);
                }
            }else if (currentIndex + sibling.getString().length() < endIndex){
                result.append(sibling);
            }else{
                for (Text text : clipEnding(endIndex,sibling)){
                    result.append(text);
                }
            }
        }
        return result;
    }
    private List<Text> clipBeginning(int index, Text text){
        String clippedString = text.getString().substring(index);
        return Text.literal(clippedString).getWithStyle(text.getStyle());
    }
    private List<Text> clipEnding(int index, Text text){
        String clippedString = text.getString().substring(0,index);
        return Text.literal(clippedString).getWithStyle(text.getStyle());
    }
}
