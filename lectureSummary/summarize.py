from dotenv import load_dotenv
from openai import OpenAI
load_dotenv()

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

def summarize_text(text):
    try:
        print("Generating summary...")
        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {
                    "role": "system",
                    "content": "You are a helpful assistant that summarizes lecture transcripts into concise summaries."
                },
                {
                    "role": "user",
                    "content": f"Summarize the following lecture transcript:\n\n{text}"
                }
            ],
            max_tokens=300,
            temperature=0.5,
        )
        summary = response.choices[0].message.content
        print("Summary generated.")
        return summary
    except Exception as e:
        print(f"Error generating summary: {str(e)}")
        return None

