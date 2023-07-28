# Tikrow Notification

The program is used to send notifications to the e-mail address(es) when an offer appears on the Tikrow platform. In order to configure e.g. city, distance, your account, do it in the Tikrow application or on the website tikrow.com. The program does not collect data or send it anywhere. Login data is used only to log in to your Tikrow account.
The program is a console application. Notifications work when the app is running and successfully logged in.

## How run?

If you download .jar file, remember you need have at least [Java JDK 17](https://www.oracle.com/java/technologies/downloads/) installed.

You can check Java version:

```bash
java --version
```

Next you can run it:

```bash
java -jar location_of_file
```


## Code

If you use code and want to run you have to enter e-mail properties and authorization in Mail class.

#### Properties:

```java
private static Properties getProperties(){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        return prop;
}
```

#### Username and password from which notifications will be sent:


```java
private static Session getSession(){
        Session session = Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("example@example.com", "password");
            }
        });
        return session;
    }

    public static void send(String to, String subject, String textMessage) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress("example@example.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(textMessage, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
}
```
Two lines you have to change:
```java
return new PasswordAuthentication("example@example.com", "password");
```

```java
message.setFrom(new InternetAddress("example@example.com"));
```
