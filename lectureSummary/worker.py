import sys
import json
import os
import yt_dlp as ytdlp

from download_service import download
from whisper_service import transcribe_audio
from summarize import summarize_text


def get_video_title(video_url):
    """Extract video title from YouTube URL using yt-dlp"""
    try:
        with ytdlp.YoutubeDL({'quiet': True}) as ydl:
            info = ydl.extract_info(video_url, download=False)
            return info.get('title', 'Unknown Title')
    except Exception as e:
        print(f"Error extracting title: {str(e)}", file=sys.stderr)
        return "Unknown Title"


def process_video(job_id, video_url):
    """Process video and return structured result with title, transcription, and summary"""
    try:
        # Get video title
        title = get_video_title(video_url)

        # Download audio
        audio_file = download(job_id, video_url)
        if not audio_file:
            raise Exception("Failed to download audio")

        # Transcribe audio
        transcription = transcribe_audio(audio_file)
        if not transcription:
            raise Exception("Failed to transcribe audio")

        # Summarize text
        summary = summarize_text(transcription)
        if not summary:
            raise Exception("Failed to generate summary")

        # Return structured result
        return {
            "success": True,
            "title": title,
            "transcription": transcription,
            "summary": summary,
            "error": None
        }
    except Exception as e:
        error_msg = f"Error processing job {job_id}: {str(e)}"
        print(error_msg, file=sys.stderr)
        return {
            "success": False,
            "title": None,
            "transcription": None,
            "summary": None,
            "error": str(e)
        }


def main():
    """Main entry point - expects job_id and video_url as command line arguments"""
    if len(sys.argv) < 3:
        error_response = {
            "success": False,
            "title": None,
            "transcription": None,
            "summary": None,
            "error": "Missing required arguments: job_id, video_url"
        }
        print(json.dumps(error_response))
        sys.exit(1)

    job_id = sys.argv[1]
    video_url = sys.argv[2]

    result = process_video(job_id, video_url)

    # Output result as JSON to stdout
    print(json.dumps(result))


if __name__ == "__main__":
    main()