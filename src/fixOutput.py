import sys       
import subprocess
import re
import os

def log2hist(logfilename):
	# fill in your code here

	print "opened file:", logfilename


	f_in = open(logfilename, 'r')   # open log for reading
	match1 = re.search(r'results(\d+).txt', logfilename)

	for line in f_in:
		match2 = re.search(r'Beginning test for B = (\d)', line)
		match3 = re.search(r'Test # (\d) completed in ([\d.]+) seconds, took (\d+) attempts', line)
		match4 = re.search(r'Tests completed', line)


		if match2:
			f_out = open('./results/B' + match2.group(1) + '_' + match1.group(1) + '.txt', 'w')
		elif match3:
			f_out.write(str(match3.group(2)) + ' ' + str(match3.group(3)) + '\n')
		elif match4:
			f_out.close()
		else:
			continue

	f_in.close()


	return

if __name__ == '__main__':
	# get the log file name from command line
	directory = os.path.dirname(os.path.abspath(__file__))

	print directory

	for file in os.listdir(directory):

		if file.endswith(".txt"): 
			log2hist(file)
			continue
		else:
			continue

# line above may be changed to log2hist("log") to make the file name
#    always be log