"use server";

import { FormStatusProps } from "@/components/form/FormStatus";
import { revalidatePath } from "next/cache";

export type State = {
  message: string;
  bookId: string;
  status: FormStatusProps["status"];
};

type AddReadingProgressRequest = {
  dateRead: string;
  lastPageRead: number;
};

export async function addReadingProgress(
  { bookId }: State,
  formData: FormData
): Promise<State> {
  const newProgress: AddReadingProgressRequest = {
    dateRead: formData.get("dateRead") as string,
    lastPageRead: Number(formData.get("lastPageRead")),
  };

  const response = await fetch(
    `http://localhost:8080/books/${bookId}/reading-progress`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newProgress),
    }
  );

  if (response.ok) {
    revalidatePath(`/history/${bookId}`);
    return {
      bookId,
      message: "Reading progress added successfully",
      status: "success",
    };
  }

  let message = "";
  try {
    const data = await response.json();
    message = data.message;
  } catch (error) {
    console.error(`Failed to parse error message: ${error}`);
  }

  return {
    message: `Failed to add reading progress: ${response.status} ${response.statusText}. ${message}`,
    bookId,
    status: "error",
  };
}

export async function deleteReadingProgress(progressId: string) {
  const response = await fetch(
    `http://localhost:8080/reading-progress/${progressId}`,
    {
      method: "DELETE",
    }
  );

  if (response.ok) {
    revalidatePath("/history/[bookId]");
    return {
      message: "Reading progress deleted successfully",
      status: "success",
    };
  }

  let message = "";
  try {
    const data = await response.json();
    message = data.message;
  } catch (error) {
    console.error(`Failed to parse error message: ${error}`);
  }

  return {
    message: `Failed to delete reading progress: ${response.status} ${response.statusText}. ${message}`,
    status: "error" as const,
  };
}
