CREATE TABLE IF NOT EXISTS quizUser (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v1(),
	userName VARCHAR(30) NOT NULL,
	userPassword VARCHAR(50) NOT NULL,
	userPasswordHash TEXT NOT NULL,

	CONSTRAINT unique_userName UNIQUE (userName),
	CONSTRAINT chk_userName CHECK (char_length(userName) >= 3),
	CONSTRAINT chk_userPassword CHECK (char_length(userPassword) >= 8)
);

CREATE INDEX IF NOT EXISTS idx_quiz_user_id ON quizUser (id);
CREATE INDEX IF NOT EXISTS idx_quiz_user_name ON quizUser (username);

CREATE TABLE IF NOT EXISTS quizItem (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v1(),
	title VARCHAR(50) NOT NULL,
	text VARCHAR(250) NOT NULL,
	choices VARCHAR(50) [] NOT NULL,
	answers INT [],
	
	CONSTRAINT chk_title CHECK (char_length(title) >= 1),
	CONSTRAINT chk_userPassword CHECK (char_length(text) >= 1),
	CONSTRAINT check_choices CHECK (array_length(choices,1) >= 2 AND array_length(choices,1) <= 4)
);

ALTER TABLE quizItem
	ADD userId UUID NOT NULL,
	ADD CONSTRAINT userIdFK FOREIGN KEY (userId) REFERENCES quizUser(id) ON DELETE CASCADE;

CREATE INDEX IF NOT EXISTS idx_quizItem_id ON quizItem (id);

CREATE TABLE IF NOT EXISTS quizUserHistory(
	id UUID DEFAULT uuid_generate_v1() NOT NULL,
	userId UUID DEFAULT uuid_generate_v1() NOT NULL,
	quizId UUID DEFAULT uuid_generate_v1() NOT NULL,
	finishTime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	status VARCHAR(20) NOT NULL,
	PRIMARY KEY (id, userId, quizId),
	CONSTRAINT userIdFK FOREIGN KEY (userId) REFERENCES quizUser(id) ON DELETE CASCADE,
	CONSTRAINT quizIdFK FOREIGN KEY (quizId) REFERENCES quizItem(id) ON DELETE CASCADE
);
