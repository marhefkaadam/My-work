# ASCII Art

The idea of this project is to load images, translate them into ASCII ART images, optionally apply filters, and save them.

## Allowed arguments
- --image <path> - import image from path, file type can be ".jpg" or ".png"
- --image-random - generate random image, max size is set to 800x800
- --invert - use invert filter on image
- --flip <axis> - use flip filter on image - axis can be "x" or "y" 
- --brightness <value> - use brightness filter on image, value can be any float number
- --table <tableName> - convert image with specified conversion table, tableName can be "PaulBourke" or "CrazyNonLinear"
- --custom-table <values> - convert image using user defined linear conversion table, <values> should be ASCII symbols
- --output-file <path> - export converted ASCII image into file to specified path
- --output-console - export converted ASCII image into console

## Argument constraints
- there must be only one --image* argument specified
- there must be at least one --output* argument specified
- there can not be more than one --table* argument specified
- if no --table* argument is specified - PaulBourke table is used for converting
