package com.hats.plannit.notifications;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EMailSender extends AsyncTask<Void,Void,Void>
{
    private Session mSession;

    private String mEmail;
    private String mSubject;
    private String mMessage;

    public EMailSender(String mEmail, String mSubject, String mMessage)
    {
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
    }

    protected Void doInBackground(Void... params)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getDefaultInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
            }
        });

        try
        {
            MimeMessage message = new MimeMessage(mSession);
            message.setFrom(new InternetAddress(Utils.EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            message.setSubject(mSubject);
            message.setText(mMessage);
            Transport.send(message);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
