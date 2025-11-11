# React + Typescript + Vite + Java Backend + Python Worker

- Python libraries used: yt_dlp, openai whisper, ffmpeg, using groq api for LLM.

# The flow of the program

- Frontend and backend are two separate applications.
- They communicate via HTTP requests (REST API).
- Once we have a link + submit button on the frontend, we must send a request to the backend.
- The request is sent using fetch from the client-side frontend.
- The backend receives the request using FastAPI and runs code like yt_dlp.
- The backend sends a response back to the frontend in the form of JSON.
- The frontend receives the response and updates the UI accordingly. The UI is updated using React's state management (useState, useEffect).
