SRC := src
BIN := bin
LIB := lib

XSD := $(wildcard *.xsd)

all: $(BIN)/Run.class
	java -cp "$(LIB)/*:$(BIN)" Run
	java -cp "$(BIN):$(LIB)/*" Run

$(XSD): $(BIN)/Run.class
	java -cp "$(LIB)/*:$(BIN)" Run $@
	java -cp "$(BIN):$(LIB)/*" Run $@

.PHONY: $(XSD)

$(BIN)/Run.class: $(SRC)/Run.java
	javac -d $(BIN) -sourcepath $(SRC) $<

clean:
	$(RM) -r $(BIN)
