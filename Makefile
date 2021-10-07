SRC := src
BIN := bin
LIB := lib

XSD := $(wildcard *.xsd)
RUN := $(addprefix run-,$(XSD))
JARS := $(wildcard $(LIB)/*.jar)
CP := $(subst $() $(),:,$(JARS))

run: compile
	@echo With original CMStateSet:
	java -cp $(CP):$(BIN) Run
	@echo With sparse CMStateSet:
	java -cp $(BIN):$(CP) Run

$(RUN): compile
	@echo With original CMStateSet:
	java -cp $(CP):$(BIN) Run $(subst run-,,$@)
	@echo With sparse CMStateSet:
	java -cp $(BIN):$(CP) Run $(subst run-,,$@)

compile:
	javac -d $(BIN) -sourcepath $(SRC) $(SRC)/Run.java

clean:
	$(RM) -r $(BIN)
