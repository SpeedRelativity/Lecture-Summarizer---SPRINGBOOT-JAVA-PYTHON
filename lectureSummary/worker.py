import yt_dlp as ytdlp
import sys
from time import sleep
import os



def download(job,url):

    try:

        base_path = os.path.dirname(os.path.abspath(__file__))
        output_path = os.path.join(base_path, 'audios')

        if not os.path.exists(output_path):
            os.makedirs(output_path)
    
        ydl_opts = {
            'format': 'best',
            'outtmpl': f'{output_path}/%(title)s.%(ext)s',
            'postprocessors': [{
                'key': 'FFmpegExtractAudio',
                'preferredcodec': 'mp3',
                'preferredquality': '192',
            }],
            
            "overwrite": True,
            "nopostoverwrites": False,
            }

        with ytdlp.YoutubeDL(ydl_opts) as ydl:
            ydl.download([url])

        return True
    except Exception as e:
        print(f"Error downloading {url}: {str(e)}")
        return False



    
    sleep(5)

    


def main():

    job = sys.argv[1]
    url = sys.argv[2]

    try:
        result = download(job,url)
        print(f"Download completed for job {job}: {result}")
    except Exception as e:
        print(f"Error processing job {job}: {str(e)}")
    return result

if __name__ == "__main__":
    main()