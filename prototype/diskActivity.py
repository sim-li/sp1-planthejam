import os
path = "/"
while True:
    for root, dirs, files in os.walk(path):
        for name in files:
            fname = os.path.join(root, name)
            try:
                with open(fname) as f:
                    print f.readlines()
            except IOError:
                print "Huhu"
