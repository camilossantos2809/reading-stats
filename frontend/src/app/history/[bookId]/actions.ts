"use server";

import { revalidatePath } from "next/cache";

type State = {
  message: string;
  bookId: string;
};

export async function addReadingProgress(
  { bookId }: State,
  formData: FormData
) {
  const progress = formData.get("progress");

  const response = await fetch(
    `http://localhost:8080/books/${bookId}/reading-progress`,
    {
      method: "POST",
      body: JSON.stringify(progress),
    }
  );

  if (response.ok) {
    revalidatePath(`/history/${bookId}`);
  }

  return {
    message: "Failed to add reading progress",
    bookId,
  };
}
