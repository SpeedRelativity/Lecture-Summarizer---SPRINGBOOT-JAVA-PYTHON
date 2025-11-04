from dotenv import load_dotenv
from openai import OpenAI
import os

from groq import Groq

load_dotenv()

client = Groq()


#client = OpenAI(api_key=os.getenv("GROQ_API_KEY"))

def summarize_text(text):
    try:
        print("Generating summary...")
        response = client.chat.completions.create(
            model="llama-3.3-70b-versatile",
            messages=[
            {
                "role": "system",
                "content": "You are a lecture summarizer assistant that summarizes video transcripts into concise summaries and study notes."
            },
            {
                "role": "user",
                "content": f"Summarize the following lecture transcript:\n\n{text}"
            }
            ],
            temperature=1,
            max_completion_tokens=1024,
            top_p=1,
            stream=False,
            stop=None
        )
        summary = response.choices[0].message.content
        print("Summary generated.")
        return summary
    except Exception as e:
        print(f"Error generating summary: {str(e)}")
        return None

