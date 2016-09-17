java Parser $1
java Compiler $1

dot -Tpng $1.pt.dot -o $1.pt.dot.png
dot -Tpng $1.ast.dot -o $1.ast.dot.png
#dot -Tpng $1.ast.dot -o $1.ast.dot.png
dot -Tpng $1.cfg.dot -o $1.cfg.dot.png


