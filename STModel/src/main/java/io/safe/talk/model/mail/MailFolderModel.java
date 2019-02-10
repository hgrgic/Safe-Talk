package io.safe.talk.model.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.mail.Message;

@Getter
@Setter
@NoArgsConstructor
public class MailFolderModel {

    private String folderName;
    private int mailCount;
    private Message mails[];


    public MailFolderModel(String folderName, int mailCount, Message[] mails) {
        this.folderName = folderName;
        this.mailCount = mailCount;
        this.mails = mails;
    }
}
