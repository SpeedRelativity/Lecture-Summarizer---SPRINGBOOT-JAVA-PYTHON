#!/bin/bash

# Color codes for terminal output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}Lecture Summarizer - Startup${NC}"
echo -e "${BLUE}================================${NC}"

# Check if we're in the right directory
if [ ! -f "pom.xml" ]; then
    echo -e "${YELLOW}⚠️  Please run this script from the project root directory${NC}"
    exit 1
fi

# Check Maven
echo -e "\n${YELLOW}Checking Maven...${NC}"
if ! command -v mvn &> /dev/null; then
    echo -e "${YELLOW}⚠️  Maven not found. Installing...${NC}"
    sudo apt-get update
    sudo apt-get install maven -y
fi
echo -e "${GREEN}✓ Maven found${NC}"

# Check Java
echo -e "\n${YELLOW}Checking Java...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${YELLOW}⚠️  Java not found. Installing...${NC}"
    sudo apt-get install default-jdk -y
fi
echo -e "${GREEN}✓ Java found$(java --version 2>&1 | head -1)${NC}"

# Check Python venv
echo -e "\n${YELLOW}Checking Python virtual environment...${NC}"
if [ ! -d "venv" ]; then
    echo -e "${YELLOW}Creating virtual environment...${NC}"
    python3 -m venv venv
fi
echo -e "${GREEN}✓ Virtual environment exists${NC}"

# Activate Python venv
echo -e "\n${YELLOW}Activating Python virtual environment...${NC}"
source venv/bin/activate
echo -e "${GREEN}✓ Virtual environment activated${NC}"

# Check PostgreSQL
echo -e "\n${YELLOW}Checking PostgreSQL...${NC}"
if ! sudo service postgresql status > /dev/null 2>&1; then
    echo -e "${YELLOW}⚠️  PostgreSQL not running. Starting...${NC}"
    sudo service postgresql start
fi
echo -e "${GREEN}✓ PostgreSQL is running${NC}"

# First time Maven build check
if [ ! -d "target" ]; then
    echo -e "\n${YELLOW}First time setup: Building Maven project...${NC}"
    mvn clean install
    echo -e "${GREEN}✓ Build complete${NC}"
fi

echo -e "\n${BLUE}================================${NC}"
echo -e "${GREEN}Setup complete!${NC}"
echo -e "${BLUE}================================${NC}"
echo ""
echo -e "${YELLOW}To start the servers, open TWO terminals:${NC}"
echo ""
echo -e "${GREEN}Terminal 1 - Backend:${NC}"
echo -e "  cd $(pwd)"
echo -e "  mvn spring-boot:run"
echo ""
echo -e "${GREEN}Terminal 2 - Frontend:${NC}"
echo -e "  cd $(pwd)/frontend"
echo -e "  npm run dev"
echo ""
echo -e "${BLUE}Then open: ${GREEN}http://localhost:5173${NC}"
