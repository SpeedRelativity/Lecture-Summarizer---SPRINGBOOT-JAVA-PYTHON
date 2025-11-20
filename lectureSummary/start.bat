@echo off
echo Starting Lecture Summary Application...
echo.

echo Starting Python FastAPI worker...
start "Python Worker" cmd /k "cd python-worker && venv\Scripts\activate && python api.py"
timeout /t 3 /nobreak >nul

echo Starting Spring Boot backend...
start "Spring Boot" cmd /k "cd springboot-backend && mvnw.cmd spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting React frontend...
start "React Frontend" cmd /k "cd frontend && npm run dev"

echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo All services started in separate windows!
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo Python:   http://localhost:8001/
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:5173
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
pause