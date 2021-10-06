SRC := src
BIN := bin
LIB := lib

XSD := $(wildcard *.xsd)
RUN := $(addprefix run-,$(XSD))
JARS := $(wildcard $(LIB)/*.jar)
CP := $(subst $() $(),:,$(JARS))

compile:
	javac -d $(BIN) -sourcepath $(SRC) $(SRC)/Run.java

$(RUN): compile
	@echo With original CMStateSet:
	java -cp $(CP):$(BIN) Run $(subst run-,,$@)
	@echo With sparse CMStateSet:
	java -cp $(BIN):$(CP) Run $(subst run-,,$@)

clean:
	$(RM) -r $(BIN)
