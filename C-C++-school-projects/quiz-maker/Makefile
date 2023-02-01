CXX = g++
CXXFLAGS = -std=c++17 -Wall -pedantic -Wextra
FILESYSTEM = -lstdc++fs

TARGET = quiz_maker

SOURCE_DIR = src
BUILD_DIR = build
DEBUG_DIR = debug

SOURCES = $(wildcard $(SOURCE_DIR)/*.cpp)
HEADERS = $(wildcard $(SOURCE_DIR)/*.h)
TESTS = $(wildcard tests/*.cpp)

OBJECTS = $(SOURCES:$(SOURCE_DIR)/%.cpp=$(BUILD_DIR)/%.o)
DEBUG_O = $(SOURCES:$(SOURCE_DIR)/%.cpp=$(DEBUG_DIR)/%.o)
DEBUG_O_TEST = $(TESTS:tests/%.cpp=$(DEBUG_DIR)/tests/%.o)

all: compile doc
	@echo "Makefile finished."

run: $(TARGET)
	./$(TARGET)

compile: $(TARGET)
	@echo "Compiling finished."

$(TARGET): $(OBJECTS)
	$(CXX) $(CXXFLAGS) $^ -o $@ $(FILESYSTEM)

$(BUILD_DIR)/%.o: $(SOURCE_DIR)/%.cpp
	mkdir -p $(BUILD_DIR)
	$(CXX) $(CXXFLAGS) $< -c -o $@


test: debugging
	./debugging

debugging:  $(DEBUG_O_TEST) $(filter-out debug/main.o,$(DEBUG_O))
	$(CXX) $(CXXFLAGS) $^ -o $@ $(FILESYSTEM)

$(DEBUG_DIR)/tests/%.o: tests/%.cpp
	mkdir -p $(DEBUG_DIR)/tests
	$(CXX) $(CXXFLAGS) $< -c -o $@

$(DEBUG_DIR)/%.o: $(filter-out src/main.cpp,$(SOURCE_DIR)/%.cpp)
	mkdir -p $(DEBUG_DIR)
	$(CXX) $(CXXFLAGS) $< -c -o $@


doc: Doxyfile $(HEADERS)
	doxygen Doxyfile

clean:
	rm -rf $(TARGET) debugging $(BUILD_DIR)/ doc/ debug/ 2>/dev/null


# dependencies
# g++ -MM src/*.cpp | sed -e 's|^C|build/C|'
build/CApplication.o: src/CApplication.cpp src/CApplication.h src/CQuiz.h \
 src/CQuestion.h src/CInterface.h src/CInteraction.h src/messages.h
build/CInteraction.o: src/CInteraction.cpp src/CInteraction.h src/CInterface.h \
 src/messages.h src/CQuiz.h src/CQuestion.h src/headers.h
build/CInterface.o: src/CInterface.cpp src/CInterface.h src/messages.h
build/CQMultiChoice.o: src/CQMultiChoice.cpp src/CQMultiChoice.h \
 src/CQuestion.h src/CInterface.h src/messages.h src/headers.h
build/CQSingleChoice.o: src/CQSingleChoice.cpp src/CQSingleChoice.h \
 src/CQuestion.h src/CInterface.h src/messages.h \
 src/headers.h
build/CQText.o: src/CQText.cpp src/CQText.h src/CQuestion.h src/CInterface.h \
 src/messages.h src/headers.h
build/CQuestion.o: src/CQuestion.cpp src/CQuestion.h src/CInterface.h \
 src/headers.h
build/CQuiz.o: src/CQuiz.cpp src/CQuiz.h src/CQuestion.h src/CInterface.h \
 src/CQSingleChoice.h src/CQText.h src/CQMultiChoice.h \
 src/messages.h src/headers.h
build/main.o: src/main.cpp src/CApplication.h src/CQuiz.h src/CQuestion.h \
 src/CInterface.h src/CInteraction.h src/messages.h

# debug dependencies
debug/tests/Tests.o: tests/Tests.cpp tests/Tests.h src/CQMultiChoice.h \
 src/CQuestion.h src/CInterface.h \
 src/CQSingleChoice.h src/CQText.h \
 src/CQuiz.h src/CInteraction.h src/messages.h \
 src/CApplication.h
debug/tests/main.tests.o: tests/main.tests.cpp tests/Tests.h