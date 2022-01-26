#! /usr/bin/python3
import sys


class Calculator:

    def __init__(self):

        self.commands = {
            "def": self.defCommand,
            "calc": self.calcCommand,
            "clear": self.clearCommand
        }

        self.variables = {}
        self.inverse_map = {}

        return

    def defCommand(self, line):
        _, key, value = line.split(" ")
        self.variables[key] = int(value)
        self.inverse_map[int(value)] = key

    def calcCommand(self, line):

        procedure = line.split(" ")[1:-1]

        for idx in range(0, len(procedure), 2):
            if procedure[idx] not in self.variables.keys():
                print(" ".join(procedure) + " = unknown")
                return

        value = self.variables[procedure[0]]

        for idx in range(1, len(procedure) - 1, 2):
            if procedure[idx] == "+":
                value += self.variables[procedure[idx+1]]
            elif procedure[idx] == "-":
                value -= self.variables[procedure[idx+1]]
        
        result = self.inverse_map.get(value, "unknown")

        print(" ".join(procedure) + f" = {result}")

    def clearCommand(self, _):
        self.variables.clear()
        self.inverse_map.clear()

    def run(self):

        while True:
            commandLine = sys.stdin.readline()
            if not commandLine:
                break
            else:
                command = commandLine.split(" ")[0].strip("\n")
                if command in self.commands.keys():
                    self.commands[command](commandLine)


def main():
    calculator = Calculator()
    calculator.run()


if __name__ == "__main__":
    main()