TEXFILES := $(wildcard *.tex)

all: main.pdf
	

main.pdf:  $(TEXFILES)
	pdflatex main
	bibtex main
	pdflatex main
	pdflatex main


clean:
	rm -f *.aux *.bbl *.blg *.lof *.lot *.log *.toc main.pdf


