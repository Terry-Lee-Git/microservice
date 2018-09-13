docker build -t message-service:latest .

python用pip装第三方库numpy时报错：UnicodeDecodeError: 'ascii' codec can't decode byte 0xc3 in position 7: ordi
方法4：打开python27/Lib文件夹下的site.py文件,在文件开头加入三行：

import sys
reload(sys)
sys.setdefaultencoding('utf-8')