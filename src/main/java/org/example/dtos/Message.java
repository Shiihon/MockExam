package org.example.dtos;

import java.time.LocalDateTime;

public record Message(int status, String message, LocalDateTime timeOfError) {
}