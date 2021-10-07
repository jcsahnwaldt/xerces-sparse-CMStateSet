SRC := src
BIN := bin
LIB := lib

XSD := $(wildcard *.xsd)
RUN := $(addprefix run-,$(XSD))

run: compile
	@echo With original CMStateSet:
	java -cp "$(LIB)/*:$(BIN)" Run
	@echo With sparse CMStateSet:
	java -cp "$(BIN):$(LIB)/*" Run

$(RUN): compile
	@echo With original CMStateSet:
	java -cp "$(LIB)/*:$(BIN)" Run $(subst run-,,$@)
	@echo With sparse CMStateSet:
	java -cp "$(BIN):$(LIB)/*" Run $(subst run-,,$@)

compile:
	javac -d $(BIN) -sourcepath $(SRC) $(SRC)/Run.java

clean:
	$(RM) -r $(BIN)
