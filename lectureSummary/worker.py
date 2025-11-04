import sys
import json
import os

from download_service import download
from whisper_service import transcribe_audio
from summarize import summarize_text    



def process_video(job_id, video_url):

    try: 
        audio_file = download(job_id, video_url)
        transcription = transcribe_audio(audio_file)
        print(transcription)
        summary = summarize_text(transcription)
        print(summary)
        return summary
    except Exception as e:
        print(f"Error processing job {job_id}: {str(e)}")
        return None
def main():

    job = sys.argv[1]
    url = sys.argv[2]

    try:
        result = process_video(job,url)
        print(f"Download completed for job {job}: {result}")
    except Exception as e:
        print(f"Error processing job {job}: {str(e)}")
    return result

if __name__ == "__main__":
    main()