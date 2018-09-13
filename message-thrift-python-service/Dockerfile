#message-thrift-python-service Dockerfile
FROM python:3.6
MAINTAINER mayanghua "yh_ma@qq.com"

RUN pip install -U pip
RUN pip install thrift

ENV PYTHONPATH /
COPY message /message

ENTRYPOINT ["python","/message/message_service.py"]
