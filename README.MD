# 🎓 Video Summarizer using ytlib, OpenAI Whisper, LLM 

AI workflow that **automatically transcribes and summarizes lectures, talks, and YouTube videos**.  

---

## 🚀 Features

- 🎧 **Audio Transcription** — Uses OpenAI Whisper to accurately transcribe English (and other languages).  
- 🧠 **Summarization** — Generates concise summaries and study notes from long lectures or talks.  
- 🔗 **YouTube Integration** — Download and process videos directly from YouTube URLs.  
- ⚙️ **Job Queue System** — Handles multiple transcription/summarization jobs concurrently.  
- 🖥️ **Frontend Integration (in progress)** — React-based UI to view jobs, summaries, and transcripts.  
- 💾 **Database Support (up next)** — Store processed summaries for easy retrieval (WIP)

---

## 🧩 Example Run

```bash
PS C:\Users\necha\Desktop\lectureSummarize\lecturesummary> python worker.py 1000 https://www.youtube.com/watch?v=Nne_Vwmfew8
Loading Whisper model...
Whisper model loaded.
[youtube] Downloading video and extracting audio...
Transcribing audio file: audios/Nne_Vwmfew8.mp3
Detected language: English
Transcription completed.
Generating summary...
Summary generated.
