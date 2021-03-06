# WARNING: Only for execution in docker.
# This script downloads shell from apt repository, creates new user and
# installs Fury using the shell passed as command line parameter
#
# Argument 1: shell type
# Argument 2: conjunction symbol for the specified shell
#
# Example usage:
# ./installation_test bash " && "

SHELL_NAME="$1"
JOIN="$2"
apt-get install -y "${SHELL_NAME}"
HOME="/home/${SHELL_NAME}_user"
USER="${SHELL_NAME}_user"
useradd -s "$(which ${SHELL_NAME})" -d "$HOME" "$USER"
mkdir -p "$HOME"
chown "$USER" "$HOME"
git config --global user.email "${USER}@${USER}"
git config --global user.name "${USER}"
su "$USER" -c "touch $HOME/.${SHELL_NAME}rc"
su "$USER" -c "${SHELL_NAME}" -c /install.sh
su "$USER" -c "${SHELL_NAME}" -c "source /home/${SHELL_NAME}_user/.${SHELL_NAME}rc ${JOIN} fury start ${JOIN} fury about ${JOIN} fury stop"
