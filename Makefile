SRC := src
BIN := bin
LIB := lib

XSD := $(wildcard *.xsd)
RUN := $(addprefix run-,$(XSD))

run: compile
	java -cp "$(LIB)/*:$(BIN)" Run
	java -cp "$(BIN):$(LIB)/*" Run

$(RUN): run-%: % compile
	java -cp "$(LIB)/*:$(BIN)" Run $<
	java -cp "$(BIN):$(LIB)/*" Run $<

compile:
	javac -d $(BIN) -sourcepath $(SRC) $(SRC)/Run.java

clean:
	$(RM) -r $(BIN)
