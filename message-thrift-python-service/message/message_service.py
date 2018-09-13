# coding: utf-8
from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender='18825111002@163.com'
authCode='abc123'
class MessageServiceHandler:
    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage,mobile:" +mobile + ",message:" + message)
        return True
    def sendEmailMessage(self, email, message):
        print("sendEmailMessage,email:" + email+", message:"+message)
        messageObj = MIMEText(message,'plain', 'utf-8')
        messageObj['From'] = "{}".format(sender)
        receivers = [email]
        messageObj['To'] = ",".join(receivers)
        messageObj['Subject'] =Header('testpython', 'utf-8')
        try:
            smtpObj=smtplib.SMTP('smtp.163.com')
            smtpObj.login(sender,authCode)
            smtpObj.sendmail(sender,receivers,messageObj.as_string())
            print("send mail success")
            return True
        except smtplib.SMTPException as ex:
            print("send mail failed!")
            print(ex)
        return True


if __name__ == "__main__":
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    # 定义监听的端口
    transport = TSocket.TServerSocket(None, "9090")
    # 定义传输的方式
    tfactory = TTransport.TFramedTransportFactory()
    # 定义传输协议（二进制的传输协议）
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print("python thrift server start")
    server.serve()
    print("python thrift server exit")

