package com.itecheasy.common.util;



import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class Mail {
	private static Logger logger = Logger.getLogger(Mail.class.getName());
	
	private String smtpHost = null;
	private String smtpUsername = null;
	private String smtpPassword = null;
	private String sendFrom = null;
	private String subject = null;
	private String text = null;
	private String myMail = null;
	private List<String> recipients = new ArrayList<String>();
	private List<String> attachments = new ArrayList<String>();

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setMyMail(String myMail) {
		this.myMail = myMail;
	}

	public void sendMail() throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", smtpUsername == null ? "false" : "true");
		Session s = Session.getDefaultInstance(props, null);
		MimeMessage m = new MimeMessage(s);
		InternetAddress add = new InternetAddress(myMail);
		add.setPersonal(sendFrom);
		m.setFrom(add);
		if(isChain(subject)){
			m.setSubject(MimeUtility.encodeText(subject,"gb2312","B"));
		}else{
			m.setSubject(subject,"utf8");
		}
//		m.setSubject(subject);
		int recipientsCount = recipients.size();
		InternetAddress[] adds = new InternetAddress[recipientsCount];
		for (int i = 0; i < recipientsCount; i++) {
			adds[i] = new InternetAddress((String) recipients.get(i));
		}
		m.setRecipients(Message.RecipientType.TO, adds);
		Multipart mp = new MimeMultipart();
		BodyPart bpText = new MimeBodyPart();
		// bpText.setText(text);
		bpText.setContent(text, "text/html;charset=utf-8");
		mp.addBodyPart(bpText);
		int attachementsCount = attachments.size();
		for (int i = 0; i < attachementsCount; i++) {
			BodyPart bpAttachment = new MimeBodyPart();
			bpAttachment.setDisposition(Part.ATTACHMENT);
			FileDataSource fds = new FileDataSource((String) attachments.get(i));
			bpAttachment.setDataHandler(new DataHandler(fds));
			mp.addBodyPart(bpAttachment);
		}
		m.setContent(mp);
		m.saveChanges();
		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		if (smtpUsername == null) {
			transport.connect((String) props.get("mail.smtp.host"), null, null);
		} else {
			transport.connect((String) props.get("mail.smtp.host"), smtpUsername, smtpPassword);
		}
		transport.sendMessage(m, adds);
		transport.close();
	}
	
	private static boolean isChain(String s){
		Pattern pattern=Pattern.compile("[\u4e00-\u9fa5]");   
		Matcher matcher=pattern.matcher(s);  
		return matcher.find();
	}
}
