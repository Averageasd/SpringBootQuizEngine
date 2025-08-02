CREATE TABLE IF NOT EXISTS quizUser (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v1(),
	userName VARCHAR(30) NOT NULL,
	userPassword VARCHAR(50) NOT NULL,
	userPasswordHash TEXT NOT NULL,

	CONSTRAINT unique_userName UNIQUE (userName),
	CONSTRAINT chk_userName CHECK (char_length(userName) >= 3),
	CONSTRAINT chk_userPassword CHECK (char_length(userPassword) >= 8)
)

CREATE INDEX IF NOT EXISTS idx_quiz_user_id ON quizUser (id);
CREATE INDEX IF NOT EXISTS idx_quiz_user_name ON quizUser (username);