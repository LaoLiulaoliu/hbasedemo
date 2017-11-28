#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Author: Yuande Liu <miraclecome (at) gmail.com>

from __future__ import print_function, division

import json
import random
import sys
import time

def make_fake_data(path=None):
    data = None
    if path is None:
        path = '../src/main/resources/sampleLog.txt'
    with open(path) as fd:
        for line in fd:
            data = json.loads(line)
            data.update(json.loads(data['data']))
            data.pop('data')

    codes = ['008', '007', '006', '005']
    number = '0123456789'
    timestamp = time.mktime( time.strptime(data['miTime'], "%Y-%m-%d %H:%M:%S") )
    data['miTime'] = str(int(timestamp)) + '000'
    with open('/tmp/sampleLog.txt', 'w') as fdw:
        for i in range(1000):
            data['reasonCode'] = random.choice(codes)
            data['openId'] = 'HSBC' + ''.join(random.sample(number, 9))
            fdw.write(json.dumps(data)+'\n')

if __name__ == '__main__':
    if len(sys.argv) > 1:
        make_fake_data(sys.argv[1])
