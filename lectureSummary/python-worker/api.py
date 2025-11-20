import os
import sys

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel # data validation
from typing import Optional
import yt_dlp as ytdlp
import sys
import os
import uvicorn

from services.download_service import download
from services.whisper_service import transcribe_audio
from services.summarize import summarize_text

app = FastAPI(title = "Lecture Summary Worker")

class VideoRequest(BaseModel):
    job_id: str
    video_url: str

class videoResponse(BaseModel):
    success: bool
    title: Optional[str] = None
    transcript: Optional[str] = None
    summary: Optional[str] = None
    error: Optional[str] = None




def get_video_title(video_url):

    try:
        with ytdlp.YoutubeDL({'quiet': True}) as ydl:
            info = ydl.extract_info(video_url, download=False)
            return info.get('title', 'Unknown Title')
    except Exception as e:
        print(f"Error fetching video title: {str(e)}")
        return "Unknown Title"
    
# call all the services and return the final response

@app.post("/process", response_model=videoResponse)
async def process_video(request: VideoRequest):
    try:
        print(f"Processing job_id: {request.job_id}")
        print(f"Video URL: {request.video_url}")

        title = get_video_title(request.video_url)
        print(f"Video title: {title}")
    
        
        # Download audio
        print("⬇️  Downloading audio...")
        audio_file = download(request.job_id, request.video_url)
        print(f"✅ Audio file: {audio_file}")

        if not audio_file:
            raise HTTPException(status_code=500, detail="Failed to download audio")

        transcription = transcribe_audio(audio_file)
        if not transcription:
            raise HTTPException(status_code=500, detail="Failed to transcribe audio.")

        summary = summarize_text(transcription)
        if not summary:
            raise HTTPException(status_code=500, detail="Failed to generate summary.")

        return videoResponse(success=True, title=title, transcript=transcription, summary=summary, error=None)
    
    except Exception as e:
        error_message = f"Error processing video: {str(e)}"
        print(error_message)
        return videoResponse(success=False, title=None, transcript=None, summary=None, error=error_message)
    
if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8001)

