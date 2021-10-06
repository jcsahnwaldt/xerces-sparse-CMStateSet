SRC := src/main/java
BIN := build/classes/java/main
LIB := lib

XSD := $(wildcard *.xsd)
RUN := $(addprefix run-,$(XSD))
JARS := $(wildcard $(LIB)/*.jar)
CP := $(subst $() $(),:,$(JARS))

compile:
	javac -d $(BIN) $(SRC)/org/apache/xerces/impl/dtd/models/CMStateSet.java $(SRC)/Run.java

$(RUN): compile
	@echo With original CMStateSet:
	java -cp $(CP):$(BIN) Run $(subst run-,,$@)
	@echo With sparse CMStateSet:
	java -cp $(BIN):$(CP) Run $(subst run-,,$@)

clean:
	$(RM) -r bin/
