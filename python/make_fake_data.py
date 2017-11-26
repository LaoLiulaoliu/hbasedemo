#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Author: Yuande Liu <miraclecome (at) gmail.com>

from __future__ import print_function, division

import json
import random

def make_fake_data():
    data = None
    with open('../src/main/resources/sampleLog.txt') as fd:
        for line in fd:
            data = json.loads(line)
            data.update(json.loads(data['data']))
            data.pop('data')

    codes = ['008', '007', '006', '005']
    with open('/tmp/sampleLog.txt', 'w') as fdw:
        for i in range(1000):
            data['reasonCode'] = random.choice(codes)
            fdw.write(json.dumps(data)+'\n')

if __name__ == '__main__':
    make_fake_data()
