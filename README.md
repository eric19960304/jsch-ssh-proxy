# Purpose

A Proxy implementation for Jsch to achieve tcp/ip proxy through ssh.

# Usage Example

To achieve command `sftp -oProxyCommand="ssh -W %h:%p proxyServer" myServer` in Java using Jsch and SSHProxy.java in this Repo:

```
JSch jsch;
Session proxyServerSession;
Session myServerSession;
ChannelSftp channelSftp;

jsch = new JSch();
byte[] privateKey = Files.readAllBytes("private_key_path");
jsch.addIdentity("", privateKey, null, null);

proxyServerSession = jsch.getSession("proxy_username", "proxyServer_hostname", 3001);
proxyServerSession.connect();

myServerSession = jsch.getSession("my_username", "myServer_hostname", 22);
myServerSession.setProxy(new ProxySSH(proxyServerSession));   //  <======= ProxySSH class used here
myServerSession.connect();

channelSftp = (ChannelSftp) myServerSession.openChannel("sftp");
channelSftp.connect();

channelSftp.get(...);
...
```