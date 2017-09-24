#!/usr/bin/env python3

# python3.5 OneLiner.py --path ../../test/resources/Splitter/

import argparse
import glob
import re


class OneLiner:
    # CONSTANTS AND VARS

    FILE_NAME_PREFIX = "TextFile"

    # METHODS

    def __init__(self):
        parser = argparse.ArgumentParser(epilog='')

        parser.add_argument("--path", required=True, help="path to files")

        self.args = parser.parse_args()

    def run(self):
        my_one_liner = OneLiner()

        files = my_one_liner.listAllFiles()

        # forget about foreach loops
        # use list comprehension instead
        [self.oneLineAndPrint(file) for file in files]

    def listAllFiles(self):
        path = self.args.path

        files = [file for file in glob.glob(path + '/**/{0}_*.txt'.format(self.FILE_NAME_PREFIX), recursive=True)]

        return files

    def oneLineAndPrint(selfself, source):
        with open(source) as input_file:
            read_data = input_file.read()
        input_file.closed

        read_data_without_newlines = read_data.replace('\n', ' ')

        # cRaZy, to match END OF STRING you would use "$"
        # but this includes END OF STRING w or w/o newline

        # to match END OF STRING w/o newline "\z" is used,
        # EXCEPT Python, which uses "\Z"

        # link to wisdom: http://www.regular-expressions.info/anchors.html
        read_data_with_emptyline_at_end = re.sub('\Z', '\n', read_data_without_newlines)

        print(read_data_with_emptyline_at_end)


if __name__ == '__main__':
    OneLiner().run()
